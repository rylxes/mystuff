package org.webtree.mystuff.model.domain;

import java.io.Serializable;

public class AuthDetails implements Serializable {
    private static final long serialVersionUID = 6425650941625914226L;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public AuthDetails setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthDetails setPassword(String password) {
        this.password = password;
        return this;
    }
}
