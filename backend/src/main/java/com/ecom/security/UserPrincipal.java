package com.ecom.security;

import com.ecom.entity.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String displayName;
    private final String userName;
    private final String email;
    private final String password;
    private final boolean emailConfirmed;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(AppUser user) {
        this.id = user.getId();
        this.displayName = user.getDisplayName();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.emailConfirmed = user.isEmailConfirmed();
        this.authorities = Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().name())
        );
    }

    public Long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getActualUserName() {
        return userName;
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
        return emailConfirmed;
    }
}
