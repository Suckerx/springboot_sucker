package com.sucker.shiroservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)//链式调用
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    private Integer roleId;
    private String roleName;

    //定义权限的集合，一个角色拥有多个权限
    //private List<Perms> perms;

}
