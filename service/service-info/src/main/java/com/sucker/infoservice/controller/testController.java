package com.sucker.infoservice.controller;

import com.sucker.commonutils.R;
import com.sucker.servicebase.annotation.SysLog;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@Tag(name= "testController",description = "测试管理")
@RestController
@CrossOrigin
@RequestMapping("/infoservice/info")
public class testController {

    @Operation(summary = "登录",description = "详细描述:登录")
    @GetMapping("login/{username}/{password}")
    public R login(@Parameter(description = "用户名") @PathVariable String username, @Parameter(description = "密码") @PathVariable String password){
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return R.error().message("登录失败！");
        }
        return R.ok();
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
