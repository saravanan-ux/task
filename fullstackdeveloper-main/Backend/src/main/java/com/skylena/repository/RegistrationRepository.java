package com.skylena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skylena.entity.User;

@Repository
public interface RegistrationRepository extends JpaRepository<User, Long> {

}
