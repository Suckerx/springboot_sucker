package com.sucker.servicebase.exceptionhandler;

import com.sucker.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody//为了返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常");
    }

    //自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }


    //参数校验相关
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//        BindingResult bindingResult = ex.getBindingResult();
//        StringBuilder sb = new StringBuilder("校验失败:");
//        for (FieldError fieldError : bindingResult.getFieldErrors()) {
//            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
//        }
//        String msg = sb.toString();
        return R.error().message("参数错误，请检查参数");
    }

    //参数校验相关
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public R handleConstraintViolationException(ConstraintViolationException ex) {
        return R.error().message("参数错误，请检查参数");
    }

}
