package com.sucker.infoservice.controller;

import com.sucker.commonutils.R;
import com.sucker.servicebase.annotation.SysLog;

import com.sucker.servicebase.entity.User;
import com.sucker.shiroservice.service.UserService;
import com.sucker.shiroservice.utils.RedisUtils;
import com.sucker.shiroservice.utils.TokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Tag(name= "testController",description = "测试管理")
@RestController
@CrossOrigin
@RequestMapping("/infoservice/info")
public class testController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * 此接口仅做示例，测试会出错，请自行根据数据库生成实体类来测试
     */
    @Operation(summary = "登陆", description = "参数:用户名 密码")
    @PostMapping("/login")
    public R login(@Parameter(description = "用户登录类") @RequestBody User user){
        Map<String, Object> result = new HashMap<>();
        String id = user.getUserId();
        String password = user.getPassword();

        //封装用户登录数据
        com.sucker.shiroservice.entity.User userById = userService.getUserById(id);
        //执行登录方法（判断账号和密码）
        if (ObjectUtils.isEmpty(userById) || userById == null) {
            return R.error().message("用户不存在");
        }
        if (!userById.getPassword().equals(password)) {
            return R.error().message("用户名或密码错误！");
        }
        String token = TokenUtils.getToken(id);
        RedisUtils.set(id, token);
        RedisUtils.expire(id, 3, TimeUnit.DAYS);//三后过期
        Map<String, Object> hash = new HashMap<>();
        hash.put("token", token);
        hash.put("expire_time", LocalDateTime.now().plusDays(3));
        return R.ok().data(hash).message("登录成功");
    }

    @Operation(summary = "test",description = "test")
    @GetMapping("test")
    //@SysLog("test")
    //@RequiresPermissions("sys:all")
    public R test(){
        System.out.println("Test");
        return R.ok();
    }

}
