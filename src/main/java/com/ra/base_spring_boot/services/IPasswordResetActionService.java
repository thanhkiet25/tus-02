package com.ra.base_spring_boot.services;

public interface IPasswordResetActionService {
    void resetPassword(String token, String newPassword);
}
