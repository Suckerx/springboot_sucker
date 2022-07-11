package com.sucker.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException{

    //@ApiModelProperty(value = "状态码")
    private Integer code;
    //@ApiModelProperty(value = "异常信息")
    private String msg;

}
