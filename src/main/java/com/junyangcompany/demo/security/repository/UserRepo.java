package com.junyangcompany.demo.security.repository;


import com.junyangcompany.demo.security.mapping.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String Email);

    @Modifying
    @Query(value = "update user set is_valid = true where username = ?1 ",nativeQuery = true)
    void updateUserInformation(String username);
}
