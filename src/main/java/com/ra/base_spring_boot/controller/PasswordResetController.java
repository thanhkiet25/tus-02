package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.services.IPasswordResetActionService;
import com.ra.base_spring_boot.services.IPasswordResetService;
import com.ra.base_spring_boot.services.impl.PasswordResetActionServiceImpl;
import com.ra.base_spring_boot.services.impl.PasswordResetServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth/password")
public class PasswordResetController {

    private final IPasswordResetService passwordResetService;

    private final IPasswordResetActionService passwordResetActionService;

    @PostMapping("/forgot")
    public ResponseEntity<?> forgot(@RequestParam String email) {
        passwordResetService.requestPasswordReset(email);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("I have sent the password reset link to your email." +
                                " Please check your inbox and follow the instructions to reset your password.")
                        .build(), HttpStatus.OK
        );
    }

    @PostMapping("/reset")
    public ResponseEntity<?> reset(@RequestParam String token,
                        @RequestParam String newPassword) {
        passwordResetActionService.resetPassword(token, newPassword);
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .data("Password reset successfully!")
                        .build(), HttpStatus.OK
        );
    }
}
