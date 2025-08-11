package com.skylena.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skylena.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    // Find user by email
    User findByEmail(String email);

}
