package com.example.chatbotv2.services;

import com.example.chatbotv2.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImp implements UserDetails {
    private static final Long serialVersionUID=1L;

    @Setter
    private Long userid;

    @Setter
    private String username;

    @Setter
    private String email;

    @JsonIgnore
    // password not included in json output
    private final String password;

    private Collection<? extends GrantedAuthority> roles;

    public UserDetailsImp(Long userid, String username, String email, String password,
                           Collection<? extends GrantedAuthority> roles) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles=roles;
    }
    public static UserDetailsImp build(User user){
        List<GrantedAuthority> roles = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new UserDetailsImp(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public Long getUserId() {
        return userid;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
