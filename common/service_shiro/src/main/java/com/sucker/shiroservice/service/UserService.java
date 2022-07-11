package com.sucker.shiroservice.service;

import com.sucker.shiroservice.entity.Perms;
import com.sucker.shiroservice.entity.User;

import java.util.List;

public interface UserService {

    //根据用户名查询用户
    User findByUserName(String userName);

    //根据角色id查询权限集合
    String findRoleNameByRoleId(Integer id);

    //根据角色Id查询所有权限Id
    List<Perms> findPermsByRoleId(Integer roleId);

    //根据用户id查询用户
    User getUserById(String id);

}
