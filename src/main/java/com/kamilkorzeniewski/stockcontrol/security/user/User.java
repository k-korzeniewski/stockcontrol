package com.kamilkorzeniewski.stockcontrol.security.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {
    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private long id;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_password")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnore
    private Set<UserRole> roles;

    User() {
        roles = new HashSet<>();
    }


    public User(String name, String password, Set<UserRole> roles) {
        this(name, password);
        this.roles = roles;
    }

    public User(String name, String password) {
        this();
        this.name = name;
        this.password = password;
    }

    User withPassword(String password) {
        return new User(this.name, password, this.roles);
    }

    User withRoles(Set<UserRole> roles) {
        return new User(this.name, this.password, roles);

    }

    String getPassword() {
        return password;
    }


    public Set<GrantedAuthority> getAuthorities() {
        return roles.stream().map(UserRole::getAuthority).collect(Collectors.toSet());
    }

}
