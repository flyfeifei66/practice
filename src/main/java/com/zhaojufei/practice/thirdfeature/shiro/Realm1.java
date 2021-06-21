package com.zhaojufei.practice.thirdfeature.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class Realm1 implements Realm {

    @Override
    public String getName() {
        return "myrealm1";
    }

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        String username = authenticationToken.getPrincipal().toString();
        String password = new String((char[]) authenticationToken.getCredentials());
        if (!"zhang".equals(username)) {
            throw new UnknownAccountException(); // 如果用户名错误
        }
        if (!"123".equals(password)) {
            throw new IncorrectCredentialsException(); // 如果密码错误
        }

        // 如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
