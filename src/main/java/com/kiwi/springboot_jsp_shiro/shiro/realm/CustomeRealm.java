package com.kiwi.springboot_jsp_shiro.shiro.realm;

import com.kiwi.springboot_jsp_shiro.entity.Permission;
import com.kiwi.springboot_jsp_shiro.entity.Role;
import com.kiwi.springboot_jsp_shiro.entity.User;
import com.kiwi.springboot_jsp_shiro.service.UserService;
import com.kiwi.springboot_jsp_shiro.shiro.utils.ApplicationContextUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;

import java.util.List;

//自定义realm
public class CustomeRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Object primaryPrincipal = principalCollection.getPrimaryPrincipal();
        System.out.println("调用授权验证： "+primaryPrincipal.toString());

        //在工厂中获取service对象
        UserService userService = (UserService)ApplicationContextUtils.getBean("userService");
        User rolesByUserName = userService.findRolesByUserName(primaryPrincipal.toString());
        //授权角色
        if(!CollectionUtils.isEmpty(rolesByUserName.getRoles())){
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            rolesByUserName.getRoles().forEach(role -> {
                //追加所属角色
                simpleAuthorizationInfo.addRole(role.getName());
                //追加所属权限
                List<Permission> permissions= userService.findPermissionsByRoleId(role.getId());
                System.out.println("[][][][][][][]]");
                System.out.println(permissions);
                if(!CollectionUtils.isEmpty(permissions)){
                    permissions.forEach(permission -> {
                        simpleAuthorizationInfo.addStringPermission(permission.getName());
                    });
                }
            });
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("=============================");
        //获取身份信息
        String principal = (String)authenticationToken.getPrincipal();
        //在工厂中获取service对象
        UserService userService = (UserService)ApplicationContextUtils.getBean("userService");
        System.out.println(principal);
        User user = userService.findByUserName(principal);
        System.out.println(user);

        if (!ObjectUtils.isEmpty(user)){
        //返回认证结果
            return new SimpleAuthenticationInfo(
                    user.getUsername(),
                    user.getPassword(),
                    ByteSource.Util.bytes(user.getSalt()),
                    this.getName());
        }
        return null;
    }
}
