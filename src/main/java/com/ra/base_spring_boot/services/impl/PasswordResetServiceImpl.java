package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.PasswordResetToken;
import com.ra.base_spring_boot.model.User;
import com.ra.base_spring_boot.repository.IPasswordResetRepository;
import com.ra.base_spring_boot.repository.IUserRepository;
import com.ra.base_spring_boot.services.IPasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements IPasswordResetService {

    private final IPasswordResetRepository passwordResetRepository;

    private final IUserRepository userRepository;

    private final JavaMailSender javaMailSender;

    @Override
    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new HttpNotFound("Email is not exist!"));

        PasswordResetToken existingToken = passwordResetRepository.findByUser(user).orElse(null);

        String token = UUID.randomUUID().toString(); // tạo token mới

        if (existingToken != null) {
            // update token cũ
            existingToken.setToken(token);
            existingToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
            passwordResetRepository.save(existingToken);
        } else {
            // tạo token lần đầu
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setUser(user);
            resetToken.setToken(token);
            resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
            passwordResetRepository.save(resetToken);
        }

            String link = "http://localhost:8080/api/password/reset?token=" + token;
            sendMail(email, "Reset Password", "Nhấn link để đổi mật khẩu: \n" + link);

    }

    @Override
    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
