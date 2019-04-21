package com.kamilkorzeniewski.stockcontrol.security.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long id;

    @Column(name = "role")
    private String role;

    public UserRole() {
    }

    public UserRole(String role){
        this.role = role;
    }

    public UserRole(UserRoles role) {
        this.role = role.name();
    }

    GrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(role);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return id == userRole.id &&
                Objects.equals(role, userRole.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, role);
    }
}
