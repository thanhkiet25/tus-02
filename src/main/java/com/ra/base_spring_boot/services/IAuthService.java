package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.req.FormLogin;
import com.ra.base_spring_boot.dto.req.FormRegister;
import com.ra.base_spring_boot.dto.resp.JwtResponse;
import com.ra.base_spring_boot.dto.resp.UserResponse;
import com.ra.base_spring_boot.model.BlackListToken;

import java.util.List;
import java.util.Map;

public interface IAuthService
{

    UserResponse register(FormRegister formRegister);

    JwtResponse login(FormLogin formLogin);

    BlackListToken logout(String token);

    boolean isTokenInBlackList(String token);

    void deleteTokenFromBlackList();



}
