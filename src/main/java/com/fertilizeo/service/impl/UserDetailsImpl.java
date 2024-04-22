package com.fertilizeo.service.impl;

import com.fertilizeo.entity.Compte;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Compte compte;


    public static UserDetailsImpl build(Compte compte) {

        UserDetailsImpl userDetails = new UserDetailsImpl();

        return userDetails;
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {

        return this.compte.getPassword();
    }

    @Override
    public String getUsername() {
        return this.compte.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
