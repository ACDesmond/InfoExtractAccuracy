package com.nju.infoextract.controller.tmp;

import java.util.Arrays;

/**
 * @author: songqiang
 * @since: 2020/11/20
 */
public class MockUser {
    private String username;
    private String pwd;
    private String[] roles;
    private String avatar;

    public MockUser(String username, String pwd, String[] roles, String avatar) {
        this.username = username;
        this.pwd = pwd;
        this.roles = roles;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "MockUser{" +
                "username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", roles=" + Arrays.toString(roles) +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
