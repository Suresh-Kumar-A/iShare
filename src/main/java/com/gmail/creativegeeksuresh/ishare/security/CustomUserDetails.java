package com.gmail.creativegeeksuresh.ishare.security;

import java.util.Collection;

import com.gmail.creativegeeksuresh.ishare.model.User;
import com.gmail.creativegeeksuresh.ishare.service.util.CustomUtils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(CustomUtils.formatRole(user.getRole().getRoleName()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Indicates whether the user's account has expired. An expired account cannot
        // be authenticated.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Indicates whether the user is locked or unlocked. A locked user cannot be
        // authenticated.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Indicates whether the user's credentials (password) has expired. Expired
        // credentials prevent authentication.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Indicates whether the user is enabled or disabled. A disabled user cannot be
        // authenticated.
        return user.getStatus();
    }

}
