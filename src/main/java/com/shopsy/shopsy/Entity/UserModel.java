package com.shopsy.shopsy.Entity;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Component;

import com.shopsy.shopsy.Enum.RequestStatus;
import com.shopsy.shopsy.Enum.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Component
@Data
@Table(name = "User")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "user_id")
    private UUID id;

    @NotNull
    @Size(min = 3, max = 15)
    private String userName;

    @NotNull
    @Email(message = "Enter a valid email")
    private String email;

    @NotNull
    @NumberFormat(pattern = "Enter a number")
    private long phoneNumber;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.BUYER;

    // Only for Admin
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus = RequestStatus.NOTREQUESTED; // Default status is NOT_REQUESTED

    private String country = "India";

    private String state;

    private String city;

    private int pincode;

    private String streetAddress;

}
