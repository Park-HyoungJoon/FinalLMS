package com.edo.user.constant;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_USER("ROLE_USER"),ROLE_ADMIN("ROLE_ADMIN"),ROLE_TEACHER("ROLE_TEACHER");

    String value;
    Role(String value ){
        this.value = value;

    }
}
