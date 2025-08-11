package com.skylena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skylena.entity.UserOtp;

@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp, String> {
    UserOtp findByEmail(String email);
}
