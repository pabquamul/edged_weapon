package com.examples.edged_weapon.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ANONYMOUS,USER,ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
