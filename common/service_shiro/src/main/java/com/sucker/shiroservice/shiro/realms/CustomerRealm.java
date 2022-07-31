package com.sucker.shiroservice.shiro.realms;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.sucker.shiroservice.config.AuthToken;
import com.sucker.shiroservice.entity.Perms;
import com.sucker.shiroservice.entity.User;
import com.sucker.shiroservice.service.UserService;
import com.sucker.shiroservice.utils.RedisUtils;
import com.sucker.shiroservice.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;

import java.util.List;


//自定义Realm
@Slf4j
public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("执行了授权！");
        System.out.println("=================执行了授权");
        //拿到当前登录对象
        Subject subject = SecurityUtils.getSubject();

        User loginuser = (User) subject.getPrincipal();

        //获取身份信息
        //String primaryPrincipal = ((User) principalCollection.getPrimaryPrincipal()).getUsername();
        System.out.println("调用授权验证；"+ loginuser.getUsername());

        //根据主身份信息获取 角色 和 权限信息
        User user = userService.findByUserName(loginuser.getUsername());
        //授权角色信息，遍历集合
        if(!ObjectUtils.isEmpty(user.getRoleId())){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            //通过角色Id拿到角色名
            String roleNameByRoleId = userService.findRoleNameByRoleId(user.getRoleId());
            //添加角色
            simpleAuthorizationInfo.addRole(roleNameByRoleId);


            //权限信息  根据roleId获取对应权限Id，再通过权限id得到所有权限
            List<Perms> permsByRoleId = userService.findPermsByRoleId(user.getRoleId());
            if(!CollectionUtils.isEmpty(permsByRoleId) && permsByRoleId.get(0)!=null){
                permsByRoleId.forEach(perm -> {
                    simpleAuthorizationInfo.addStringPermission(perm.getPermission());
                });
            }

            return simpleAuthorizationInfo;
        }
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        log.info("执行了认证");
        //token
        AuthToken useToken=(AuthToken)authenticationToken;
        User user = null;
        log.info("jwt解析的usertoken是"+useToken.getPrincipal().toString());
        DecodedJWT tokenPayLoad = TokenUtils.getTokenPayLoad(useToken.getPrincipal().toString());
        String id = tokenPayLoad.getClaim("id").asString();
        log.info("jwt解析的id是"+id);

        //通过redis取得 token
        RedisTemplate redisTemplate= RedisUtils.redis;
        String tokenInRedis = (String) redisTemplate.opsForValue().get(id);

        //判断redis种的token
        if (tokenInRedis == null){
            log.error("token失效，请重新登录");
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        log.info("redis里面的token"+tokenInRedis);
        if (!tokenInRedis.equals(useToken.getPrincipal().toString()))
        {
            log.error("token错误");
            throw new IncorrectCredentialsException("token错误");
        }

        //redis中验证成功后，校验数据库中是否存在该用户
        try {
            user = userService.getUserById(id);
            log.info("查找的user="+user);
        } catch (Exception e) {
            log.info("id查找异常");
            e.printStackTrace();
        }

        //用户名认证
        if (user==null){
            System.out.println("用户查找失败！");
            return null;//抛出异常
        }

        //密码认证shiro做，由于该项目密码没有加密加盐，所以调用的是这个简单构造方法
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, useToken.getPrincipal().toString(), this.getName());

//        System.out.println("=========================");
//        //先伪造数据库操作
//        //用户名
//        String principal = (String) authenticationToken.getPrincipal();
//
//        User user = userService.findByUserName(principal);
//        System.out.println("认证方法："+user.getUsername());
//
//        if(!ObjectUtils.isEmpty(user)){
//            //密码认证shiro做
//            //return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), new MyByteSource(user.getSalt()),this.getName());
//            //return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),this.getName());
//            return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
//        }
        System.out.println("=======================密码验证！");
        return info;
    }
}
