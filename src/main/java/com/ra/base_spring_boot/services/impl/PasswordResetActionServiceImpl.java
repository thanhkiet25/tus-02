package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.exception.HttpConflict;
import com.ra.base_spring_boot.model.PasswordResetToken;
import com.ra.base_spring_boot.model.User;
import com.ra.base_spring_boot.repository.IPasswordResetRepository;
import com.ra.base_spring_boot.repository.IUserRepository;
import com.ra.base_spring_boot.services.IPasswordResetActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@EnableScheduling
@Service
@RequiredArgsConstructor
public class PasswordResetActionServiceImpl implements IPasswordResetActionService {
    private final IPasswordResetRepository passwordResetRepository;

    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetRepository.findByToken(token)
                .orElseThrow(() -> new HttpConflict("Invalid token."));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new HttpConflict("The token has expired. Please request a new reset link.");
        }
        if (resetToken.isUsed()) {
            throw new HttpConflict("You have already reset your password. Do you want to change it again?");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
//        passwordResetRepository.delete(resetToken);
    }

    @Scheduled(fixedRate = 60000) // 1 phút chạy 1 lần
    public void cleanExpiredTokens() {
        List<PasswordResetToken> expiredTokens =
                passwordResetRepository.findByExpiryDateBefore(LocalDateTime.now());

        if (!expiredTokens.isEmpty()) {
            passwordResetRepository.deleteAll(expiredTokens);
            System.out.println("Expired tokens have been deleted: " + expiredTokens.size());
        }
    }
}
