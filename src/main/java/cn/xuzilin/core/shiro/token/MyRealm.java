package cn.xuzilin.core.shiro.token;

import cn.xuzilin.common.po.UserEntity;
import cn.xuzilin.common.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * Created by 96428 on 2017/8/2.
 * This in electricity, io.github.cyingyo.electricity.core.shiro
 */
public class MyRealm extends AuthorizingRealm {
    @Resource
    UserService userService;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {

        UserToken token = (UserToken) authenticationToken;
        String account = token.getAccount();
        UserEntity found = userService.getByAccount(account);
        if (null == found) {
            throw new UnknownAccountException("用户名不存在");
        }
        return new SimpleAuthenticationInfo(found, found.getPassword(), getName());

    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        Object principal = principalCollection.getPrimaryPrincipal();
        if (principal instanceof UserEntity) {
            UserEntity user = (UserEntity) principal;
        }

        return authorizationInfo;
    }

    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}
