package com.kiwi.springboot_jsp_shiro.dao;

import com.kiwi.springboot_jsp_shiro.entity.Permission;
import com.kiwi.springboot_jsp_shiro.entity.Role;
import com.kiwi.springboot_jsp_shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    void save(User user);
    User findByUserName(String username);
    //根据用户名查询角色集合
    User findRolesByUserName(String username);
    //根据角色查询权限集合
    List<Permission> findPermissionsByRoleId(long roleid);

}
