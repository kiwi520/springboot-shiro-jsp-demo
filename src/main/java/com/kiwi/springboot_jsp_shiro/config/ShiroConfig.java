package com.kiwi.springboot_jsp_shiro.config;

import com.kiwi.springboot_jsp_shiro.shiro.realm.CustomeRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {
 //1.创建shiroFilter
    @Bean  //负责拦截所有请求
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //配置系统的受限资源
        //4.设置过滤器集合
        /**
         * key ：访问连接
         *      支持通配符的形式
         * value：过滤器类型
         *      shiro常用过滤器
         *          anno ：匿名访问（表明此链接所有人可以访问）
         *          authc ：认证后访问（表明此链接需登录认证成功之后可以访问）
         *
         */
        Map<String,String> map = new LinkedHashMap<>();
        map.put("/user/login","anon"); // anon 匿名访问（无需认证和授权）
        map.put("/user/register","anon"); // anon 匿名访问（无需认证和授权）
        map.put("/user/getImage","anon"); // anon 匿名访问（无需认证和授权）
        map.put("/register.jsp","anon"); // anon 匿名访问（无需认证和授权）
        map.put("/login.jsp","anon"); // anon 匿名访问（无需认证和授权）
        map.put("/**","authc"); // authc 请求资源需要认证和授权
        //设置认证界面路径URL
        shiroFilterFactoryBean.setLoginUrl("/login.jsp");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        //配置系统的公共资源
        return shiroFilterFactoryBean;
    }

    //2.创建安全管理器
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器注入realm
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }
 //3.创建自定义的realm
    @Bean
    public Realm getRealm(){
        CustomeRealm customeRealm = new CustomeRealm();
//        修改凭证校验匹配器
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 设置加密算法为md5+salt
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        hashedCredentialsMatcher.setHashIterations(1024);
        //给realm注入凭证校验匹配器
        customeRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        //开启缓存管理
        customeRealm.setCacheManager(new EhCacheManager());
        customeRealm.setCachingEnabled(true);
        customeRealm.setAuthenticationCachingEnabled(true); //开启认证缓存
        customeRealm.setAuthenticationCacheName("authenticationCache");//给认证缓存起别名
        customeRealm.setAuthorizationCachingEnabled(true); //开启授权缓存
        customeRealm.setAuthorizationCacheName("authorizationCache");//给授权缓存起别名
        return customeRealm;
    }
}
