package com.sucker.shiroservice.filters;

import java.io.IOException;
import java.io.PrintWriter;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.google.gson.Gson;
import com.sucker.commonutils.R;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sucker.shiroservice.config.AuthToken;
import com.sucker.shiroservice.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 重新onAccessDenied 实现shiro对未认证的页直接返回json 而不是重定向
 * @author sirm
 */
@Slf4j
@Component
public class ShiroFormAuthenticationFilter extends FormAuthenticationFilter {

    //跨域访问会发送option请求，请求会被shiro拦截报跨域问题
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            System.out.println("跨域option请求!");
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    //拒绝访问的请求，会调用onAccessDenied方法
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        //获取请求token，如果token不存在，直接返回
        String token = TokenUtils.getRequestToken((HttpServletRequest) request);
        System.out.println("================token: "+token);
        HttpServletResponse resp = (HttpServletResponse) response;

        if (ObjectUtils.isEmpty(token)) {
            //这里是个坑，如果不设置的接受的访问源，那么前端都会报跨域错误，因为这里还没到corsConfig里面
            resp.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader("Origin"));
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json; charset=utf-8");
            PrintWriter out = resp.getWriter();
            System.out.println("未登录！");

            String json = new Gson().toJson(R.error().message("未登录！"));

            out.write(json); // 返回自己的json
            out.flush();
            out.close();
            return false;
        }
        //执行父类登录方法（可能也是执行的验证token？），executuLogin中的createToken由下面重写
        return executeLogin(request, response);
    }



    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse){
        //获取请求token
        String token = TokenUtils.getRequestToken((HttpServletRequest) servletRequest);
        log.info("请求的token："+token);
        return new AuthToken(token);
    }


    /**
     * token失效时候调用
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader("Origin"));
        httpResponse.setCharacterEncoding("UTF-8");
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            //String json = MAPPER.writeValueAsString(CommonResult.validateFailed("token错误或者失效"));
            String json = new Gson().toJson(R.error().message("token错误或者失效！"));
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
            log.info("登录异常");
        }
        return false;
    }


}

