package com.kiwi.springboot_jsp_shiro.service;

import com.kiwi.springboot_jsp_shiro.entity.Permission;
import com.kiwi.springboot_jsp_shiro.entity.Role;
import com.kiwi.springboot_jsp_shiro.entity.User;

import java.util.List;

public interface UserService {
//     注册用户
    void register(User user);
    User findByUserName(String username);
    User findRolesByUserName(String username);
    //根据角色查询权限集合
    List<Permission> findPermissionsByRoleId(long roleid);
}
