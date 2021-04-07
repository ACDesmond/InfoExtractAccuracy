package com.nju.infoextract.controller.tmp;

/**
 * @author: songqiang
 * @since: 2020/11/20
 */
public class Token {
    private String token;

    public Token() {}

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                '}';
    }
}
