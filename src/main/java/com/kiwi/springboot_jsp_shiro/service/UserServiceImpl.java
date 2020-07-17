package com.kiwi.springboot_jsp_shiro.service;

import com.kiwi.springboot_jsp_shiro.dao.UserDao;
import com.kiwi.springboot_jsp_shiro.entity.Permission;
import com.kiwi.springboot_jsp_shiro.entity.Role;
import com.kiwi.springboot_jsp_shiro.entity.User;
import com.kiwi.springboot_jsp_shiro.shiro.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public void register(User user) {
        //处理业务dao
        //1.生成随机盐
        String salt = SaltUtils.getSalt(8);
        //2.保存随机盐
        user.setSalt(salt);
        //3.明文密码进行md5+salt+hash散列
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt,1024);
        //4保存密码
        user.setPassword(md5Hash.toHex());

        Date date1 = new Date();//获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date1);//时间存储为字符串
        ;//转换时间字符串为Timestamp
        user.setCreated(Timestamp.valueOf(str));
        userDao.save(user);
    }

    @Override
    public User findByUserName(String username) {
        User User = userDao.findByUserName(username);
        return User;
    }

    @Override
    public User findRolesByUserName(String username) {
        return userDao.findRolesByUserName(username);
    }

    @Override
    public List<Permission> findPermissionsByRoleId(long roleid) {
        return userDao.findPermissionsByRoleId(roleid);
    }
}
