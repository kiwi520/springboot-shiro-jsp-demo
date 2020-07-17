package com.kiwi.springboot_jsp_shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {
    @RequestMapping("/save")
    public String save(){
        //获取主体对象
        Subject subject= SecurityUtils.getSubject();

        //根据角色判断
        if (subject.hasRole("admin")){
            System.out.println("保存订单");
        }else{
            System.out.println("无权操作");
        }

        //根据权限判断
        if (subject.isPermitted("mg:update:*")){
            System.out.println("由此权限");
        }else {
            System.out.println("无此权限");
        }
        return "redriect:/index.jsp";
    }

    @RequestMapping("/update")
    @RequiresRoles("admin") //用来判断角色
//    @RequiresRoles(value = {"admin","user"}) //同时拥有才能有权访问
    public String update(){
            System.out.println("用注解方式判断有无此角色");
        return "redriect:/index.jsp";
    }
}
