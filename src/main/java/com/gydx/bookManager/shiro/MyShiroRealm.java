package com.gydx.bookManager.shiro;

import com.gydx.bookManager.entity.Resource;
import com.gydx.bookManager.entity.Role;
import com.gydx.bookManager.entity.User;
import com.gydx.bookManager.service.ResourceService;
import com.gydx.bookManager.service.RoleService;
import com.gydx.bookManager.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();
        List<Role> roles = roleService.getRolesByUserId(user.getId());
        List<Resource> resources = resourceService.getResourcesByUserId(user.getId());
        authorizationInfo.addRoles(roles.stream().map(Role::getName).collect(Collectors.toSet()));
        authorizationInfo.addStringPermissions(resources.stream().map(Resource::getPerms).collect(Collectors.toSet()));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName()
        );
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("user", user);

        return authenticationInfo;
    }
}
