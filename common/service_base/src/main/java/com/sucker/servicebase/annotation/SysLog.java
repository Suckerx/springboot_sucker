/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.sucker.servicebase.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author Mark sunlightcs@gmail.com
 */
@Target(ElementType.METHOD)//用于指定被修饰的注解能用于修饰哪些程序元素
@Retention(RetentionPolicy.RUNTIME)//知名该注解的生命周期（RUNTIME是运行时有效）
@Documented//用于指定该注解类将被javadoc工具提取成文档，默认是不保留的
public @interface SysLog {
	//像是方法，实际是属性
	String value() default "";
}
