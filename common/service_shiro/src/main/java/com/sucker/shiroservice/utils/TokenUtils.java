package com.sucker.shiroservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 *
 * @title: tokenUtils
 * @Auther: sln
 * @Date: 2020/12/17/11:05
 * @Description:
 */
public  class TokenUtils {
    private static String SECRET = "rewaf!Q@W#E$R";
    private static String SECRET2 = "黄河之水天上来@*]}";
    private static String SECRET3 = "黄河之5天上来@*]}";
    private static String SECRET4 = "黄河之5piyixia上来@*]}";


    /**
     * 获取请求的token
     */
    public static String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader("Authorization");
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter("Authorization");
        }
        return token;
    }

    /**
     * 生产token
     */
    public static String getToken(String id) {
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("id",id);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7); //默认7天过期
        builder.withExpiresAt(instance.getTime());//指定令牌的过期时间
        String token = builder.sign(Algorithm.HMAC256(SECRET));//签名
        return token;
    }

    /**
     * 验证token
     */
    public static DecodedJWT verify(String token) {
        //如果有任何验证异常，此处都会抛出异常
        //verify方法用于校验token
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        return decodedJWT;
    }

    /**
     * 获取token中的 payload
     */
    public static DecodedJWT getTokenPayLoad(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        return decodedJWT;
    }
}
