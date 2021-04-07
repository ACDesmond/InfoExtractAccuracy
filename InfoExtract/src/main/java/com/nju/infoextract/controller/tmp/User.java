package com.nju.infoextract.controller.tmp;

import java.util.Arrays;

/**
 * @author: songqiang
 * @since: 2020/11/20
 */
public class User {
    String name;
    String[] roles;
    String avatar;

    public User(String name, String[] roles, String avatar) {
        this.name = name;
        this.roles = roles;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", roles=" + Arrays.toString(roles) +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
