package com.sucker.shiroservice.config;

import com.sucker.shiroservice.filters.ShiroFormAuthenticationFilter;
import com.sucker.shiroservice.filters.ShiroRolesAuthorizationFilter;
import com.sucker.shiroservice.shiro.cache.RedisCacheManager;
import com.sucker.shiroservice.shiro.realms.CustomerRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.net.httpserver.AuthFilter;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  用来整合shiro框架相关的配置
 */
@Configuration
public class ShiroConfig {

    //1.创建shiroFilter  负责拦截所有请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //注册认证失败时自定义过滤器
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("authc", new ShiroFormAuthenticationFilter());
        filters.put("roles", new ShiroRolesAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filters);

        //配置系统受限资源
        //配置系统公共资源
        Map<String ,String> map  = new HashMap<>();
        //注意顺序，不受限的资源放在上面
        //这里user下面的东西或许可以使用/user/**
        map.put("/infoservice/info/login/**","anon");//anon设置为公共资源

        //swagger
        map.put("/swagger-ui.html", "anon");
        map.put("/swagger-ui/**", "anon");
        map.put("/swagger-resources/**", "anon");
        map.put("/api/**", "anon");
        map.put("/v2/**", "anon");
        map.put("/v3/**", "anon");
        map.put("/webjars/**", "anon");
        map.put("/doc.html","anon");


        map.put("/**","anon");//anon设置为公共资源

        //map.put("/**","authc");//authc 请求这个资源需要认证和授权


        //默认认证界面：就是login.jsp，这里是再配置一下
        //shiroFilterFactoryBean.setLoginUrl("/login.jsp");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }
    //2.创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        //给安全管理器设置realm
        defaultWebSecurityManager.setRealm(realm);

        return defaultWebSecurityManager;
    }

    //创建以定义realm
    @Bean
    public Realm getRealm(){
        CustomerRealm customerRealm = new CustomerRealm();
//        //修改凭证校验匹配器
//        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//        //设置加密算法为md5
//        credentialsMatcher.setHashAlgorithmName("md5");
//        //设置散列次数
//        credentialsMatcher.setHashIterations(1024);
//        customerRealm.setCredentialsMatcher(credentialsMatcher);

        //开启缓存管理器
//        customerRealm.setCacheManager(new RedisCacheManager());
//        customerRealm.setCachingEnabled(true);//开启全局缓存
//        customerRealm.setAuthenticationCachingEnabled(true);//开启认证缓存
//        customerRealm.setAuthenticationCacheName("authenticationCache");//设置认证的缓存名字
//        customerRealm.setAuthorizationCachingEnabled(true);//开启授权缓存
//        customerRealm.setAuthorizationCacheName("authorizationCache");//设置授权的缓存名字

        return  customerRealm;
    }


    //授权配置(注解鉴权方式)  若不设置defaultAdvisorAutoProxyCreator.setUsePrefix(true);
    //                      则可能注解鉴权失效
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;

    }


}
