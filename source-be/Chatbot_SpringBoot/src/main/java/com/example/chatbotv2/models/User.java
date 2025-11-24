package com.example.chatbotv2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users",uniqueConstraints = {@UniqueConstraint(columnNames = "username"),
                                           @UniqueConstraint(columnNames = "email")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 40)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    //.LAZY: only load when use getRoles()
    @ManyToMany(fetch = FetchType.LAZY) //.EAGER to fetch all roles as user is loaded,
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conversation> conversations = new ArrayList<>();

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public Long getId() {
        return userid;
    }

    public void setUserId(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Conversation> getConversations() {return conversations;}

    public void setConversations(List<Conversation> conversations) {this.conversations = conversations;}
}
