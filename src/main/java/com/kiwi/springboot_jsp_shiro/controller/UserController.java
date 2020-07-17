package com.kiwi.springboot_jsp_shiro.controller;

import com.kiwi.springboot_jsp_shiro.entity.User;
import com.kiwi.springboot_jsp_shiro.service.UserService;
import com.kiwi.springboot_jsp_shiro.shiro.utils.VerifyCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登陆
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public String login(String username,String password,String code,HttpSession httpSession) {
        //校验验证码
        Object sessioncode = httpSession.getAttribute("code");
        if (sessioncode.equals(code)) {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
            try {
                subject.login(usernamePasswordToken);
                return "redirect:/index.jsp";
            } catch (UnknownAccountException e) {
                e.printStackTrace();
                System.out.println("用户名错误");
            } catch (IncorrectCredentialsException e) {
                e.printStackTrace();
                System.out.println("密码错误");
            }

            return "redirect:/login.jsp";
        }else{
            throw new RuntimeException("验证码错误");
        }
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping("/register")
    public String register(User user){
        try{
            userService.register(user);
            return "redirect:/login.jsp";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/register.jsp";
        }
    }

    /**
     * 用户退出
     * @return
     */
    @RequestMapping("/logout")
    public String logout(){
        Subject subject= SecurityUtils.getSubject();
        subject.logout(); //退出
        return "redirect:/login.jsp";
    }

    /**
     * 验证码
     */
    @RequestMapping("/getImage")
    public void getImage(HttpSession httpSession, HttpServletResponse httpServletResponse) throws IOException {
        //生成验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);
       //验证码放入session
        httpSession.setAttribute("code",code);
        //验证码存入图片
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        httpServletResponse.setContentType("image/jpeg");
        VerifyCodeUtils.outputImage(220,60,outputStream,code);
    }
}
