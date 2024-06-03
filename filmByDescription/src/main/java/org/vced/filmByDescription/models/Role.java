package org.vced.filmByDescription.models;

import org.springframework.security.core.GrantedAuthority;

// implements GrantedAuthority т.к. нужно для CustomUserDetails
public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
