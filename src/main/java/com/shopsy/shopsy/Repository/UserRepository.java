package com.shopsy.shopsy.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopsy.shopsy.Entity.UserModel;
import com.shopsy.shopsy.Enum.RequestStatus;

public interface UserRepository extends JpaRepository<UserModel,UUID> {

    UserModel findByEmail(String email);

    List<UserModel> findByRequestStatus(RequestStatus requestStatus);
}
