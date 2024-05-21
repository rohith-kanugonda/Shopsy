package com.shopsy.shopsy.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopsy.shopsy.Dto.LoginModel;
import com.shopsy.shopsy.Entity.UserModel;
import com.shopsy.shopsy.Service.Implementation.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("adduser")
    public ResponseEntity<Object> addUser(@Valid @RequestBody Object userOrService, BindingResult bindingResult,
            @RequestHeader String role) {
        return userService.userRegisterService(userOrService, bindingResult, role);
    }

    // @GetMapping("login")
    // public ResponseEntity<Object> verifyUser(@RequestBody LoginModel loginModel,
    // @RequestHeader String role) {
    // return userService.userLoginService(loginModel, role);
    // }

    @PostMapping("login")
    public ResponseEntity<Object> verifyUser(@RequestBody LoginModel loginModel, @RequestHeader String role) {
        return userService.userLoginService(loginModel, role);
    }

    @PostMapping("adminlogin")
    public ResponseEntity<Object> verifyAdmin(@RequestBody LoginModel loginModel, @RequestHeader String role,
            @RequestHeader String userRole) {
        return userService.adminLoginService(loginModel, role, userRole);
    }

    @PostMapping("2fa")
    public ResponseEntity<Object> twofa(@RequestHeader int otpforTwoFAFromUser, @RequestHeader String email,
            @RequestHeader String role) {
        return userService.TwoFAService(otpforTwoFAFromUser, email, role);
    }

    @PostMapping("forgotpassword")
    public ResponseEntity<Object> forgotPassword(@RequestHeader String email, @RequestHeader String role) {
        return userService.forgotPasswordService(email, role);
    }

    @PostMapping("verifyOtpforforgotpassword")
    public ResponseEntity<Object> verifyTheUserOtp(@RequestHeader int otp, @RequestHeader String email) {
        return userService.verifyTheOtpEnteredByUser(otp, email);
    }

    @PostMapping("resetpassword")
    public ResponseEntity<Object> resetThePassword(@RequestHeader String passwordFromUser, @RequestHeader String role,
            @RequestHeader String email) {
        return userService.resetThePasswordService(passwordFromUser, role, email);
    }

    // Endpoint for Becoming Seller Request
    @PostMapping("/request")
    public ResponseEntity<Object> requestToBecomeBuyer(
            @RequestBody UserModel userModel,
            @RequestHeader String token) {
        return userService.requestToBecomeBuyer(userModel, token);
    }

    // Endpoint to Access All user By Admin
    @GetMapping("/GetallUsers")
    public ResponseEntity<Object> getAllUsers(@RequestHeader String token) {
        return userService.GetAllUsers(token);
    }

    @GetMapping("getuserdetailsbytoken")
    public ResponseEntity<Object> getUserDetailsByToken(@RequestHeader String token, @RequestHeader String role) {
        return userService.getUserDetailsByEmailService(token, role);
    }
}
