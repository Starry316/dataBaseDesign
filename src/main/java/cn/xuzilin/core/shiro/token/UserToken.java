package cn.xuzilin.core.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by Starry on 2018/7/15.
 */
public class UserToken extends UsernamePasswordToken {
    private String account;
    private String pass;
    private boolean rememberMe;
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }

    @Override
    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
    @Override
    public Object getPrincipal() {
        return account;
    }

    @Override
    public Object getCredentials() {
        return pass;
    }


}
