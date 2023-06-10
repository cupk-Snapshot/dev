package com.cupk.snapshot.domain.model;

import com.cupk.snapshot.domain.model.vo.UserVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * Create by Guo Tianyou on 2023/6/6.
 */
public class User implements UserDetails {
    private final Set<SimpleGrantedAuthority> authorities;
    private final String password;
    private final String username;
    private final UserVo userVo;

    public User(Set<SimpleGrantedAuthority> authorities, String password, String username, UserVo userVo) {
        this.authorities = authorities;
        this.password = password;
        this.username = username;
        this.userVo = userVo;
    }

    @Override
    public String toString() {
        return "User{" +
                "authorities=" + authorities +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", userVo=" + userVo +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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

    public UserVo getUserVo() {
        return userVo;
    }

}
