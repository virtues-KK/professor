package com.junyangcompany.demo.security.repository;

import com.junyangcompany.demo.security.mapping.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    Role findByName(String roleUser);
}
