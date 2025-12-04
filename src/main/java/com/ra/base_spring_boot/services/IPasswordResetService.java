package com.ra.base_spring_boot.services;

public interface IPasswordResetService {
    void requestPasswordReset(String email);

    void sendMail(String to, String subject, String text);
}
