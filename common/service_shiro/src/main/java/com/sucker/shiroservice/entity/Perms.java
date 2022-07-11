package com.sucker.shiroservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)//链式调用
@AllArgsConstructor
@NoArgsConstructor
public class Perms implements Serializable {

    private Integer permissionId;
    //@NotBlank(message = "权限名不能为空！")
    private String permissionName;
    //@NotBlank(message = "权限不能为空！")
    private String permission;

}
