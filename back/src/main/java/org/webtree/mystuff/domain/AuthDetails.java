package org.webtree.mystuff.domain;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Data
public class AuthDetails implements Serializable {
    private static final long serialVersionUID = 6425650941625914226L;
    private String username;
    private String password;
}
