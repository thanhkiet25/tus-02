package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.services.IPasswordResetActionService;
import com.ra.base_spring_boot.services.IPasswordResetService;
import com.ra.base_spring_boot.services.impl.PasswordResetActionServiceImpl;
import com.ra.base_spring_boot.services.impl.PasswordResetServiceImpl;
import lombok.RequiredArgsConstructor;
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
    public String forgot(@RequestParam String email) {
        passwordResetService.requestPasswordReset(email);
        return "Đã gửi email reset mật khẩu";
    }

    @PostMapping("/reset")
    public String reset(@RequestParam String token,
                        @RequestParam String newPassword) {
        passwordResetActionService.resetPassword(token, newPassword);
        return "Đổi mật khẩu thành công";
    }
}
