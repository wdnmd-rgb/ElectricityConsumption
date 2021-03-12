package com.shiro.remal;

import com.dao.UserDao;
import com.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class MyRemal
        extends AuthorizingRealm
{
    @Autowired
    UserDao userDao;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection)
    {
        String username = principalCollection.toString();
        Set<String> roles = this.userDao.queryRoleNamesByUserName(username);
        Set<String> premissions = this.userDao.queryPermsNamesByUserName(username);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(premissions);
        return authorizationInfo;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException
    {
        String username = authenticationToken.getPrincipal().toString();
        User user = this.userDao.queryByUserName(username);
        if (user == null) {
            throw new UnknownAccountException("用户名不存在");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(), getName());
        return authenticationInfo;
    }
}
