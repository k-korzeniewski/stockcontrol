package com.kamilkorzeniewski.stockcontrol.security.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRole(String role);
}
