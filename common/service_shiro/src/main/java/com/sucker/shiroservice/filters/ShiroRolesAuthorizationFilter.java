package com.sucker.shiroservice.filters;

import java.io.IOException;
import java.io.PrintWriter;
import com.sucker.commonutils.R;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

/**
 * 重新onAccessDenied 实现shiro对未授权的页直接返回json 而不是重定向
 *
 * @author sirm
 */
public class ShiroRolesAuthorizationFilter extends RolesAuthorizationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        Subject subject = getSubject(request, response);
        String responseMessage = null;
        // 没有认证,先返回未认证的json
        if (subject.getPrincipal() == null) {
            responseMessage = new Gson().toJson(R.error().message("未认证！"));
        } else {
            // 已认证但没有角色，返回为授权的json
            responseMessage = new Gson().toJson(R.error().message("未授权！"));
        }
        out.write(responseMessage);
        out.flush();
        out.close();
        return false;
    }

}
