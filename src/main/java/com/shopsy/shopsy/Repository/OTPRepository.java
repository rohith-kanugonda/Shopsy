package com.shopsy.shopsy.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopsy.shopsy.Entity.OTPModel;

import java.util.List;
import java.time.LocalDateTime;

public interface OTPRepository extends JpaRepository<OTPModel, UUID> {

    OTPModel findByEmail(String email);

    List<OTPModel> findByCreatedAt(LocalDateTime createdAt);

}
