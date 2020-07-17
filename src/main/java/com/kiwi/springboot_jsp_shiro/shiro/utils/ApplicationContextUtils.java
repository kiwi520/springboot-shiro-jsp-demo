package com.kiwi.springboot_jsp_shiro.shiro.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context=applicationContext;
    }

    //根据bean的名称获取工厂中指定的bean对象
    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }
}
