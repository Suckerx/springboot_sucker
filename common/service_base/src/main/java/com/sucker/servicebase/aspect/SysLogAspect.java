/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.sucker.servicebase.aspect;
import com.sucker.commonutils.R;
import com.google.gson.Gson;
import com.sucker.servicebase.annotation.SysLog;
import com.sucker.servicebase.entity.SysLogEntity;
import com.sucker.servicebase.service.SysLogService;
import com.sucker.servicebase.utils.HttpContextUtil;
import com.sucker.servicebase.utils.IPUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 系统日志，切面处理类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Aspect
@Component
public class SysLogAspect {
	@Autowired
	private SysLogService sysLogService;

	@Pointcut("@annotation(com.sucker.servicebase.annotation.SysLog)")
	public void logPointCut() {

	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//保存日志
		saveSysLog(point, time,result);
		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time,Object result) {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		R result1= (R) result;

		SysLogEntity sysLog = new SysLogEntity();
		SysLog syslog = method.getAnnotation(SysLog.class);

		if(syslog != null){
			//注解上的描述
			sysLog.setOperation(syslog.value());
		}
		if (result1.getCode()!=20000) {
			return;
		}
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");

		sysLog.setResult(result1.getMessage());
		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = new Gson().toJson(args);
			sysLog.setParams(params);
		}catch (Exception e){

		}

		//获取request
		HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
		//设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));

		//用户名
		//System.out.println(((com.sucker.shiroservice.entity.User) SecurityUtils.getSubject().getPrincipal()).getUsername());
		//String username = (String) SecurityUtils.getSubject().getPrincipal();
		String username = ((com.sucker.shiroservice.entity.User) SecurityUtils.getSubject().getPrincipal()).getUsername();
		//String username = "test";
		sysLog.setUsername(username);

		sysLog.setTime(time);
		sysLog.setCreateDate(new Date());

		//保存系统日志
		sysLogService.save(sysLog);
	}
}
