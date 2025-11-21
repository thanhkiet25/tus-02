package com.ra.base_spring_boot.services;

import com.ra.base_spring_boot.dto.req.FormLogin;
import com.ra.base_spring_boot.dto.req.FormRegister;
import com.ra.base_spring_boot.dto.resp.JwtResponse;
import com.ra.base_spring_boot.dto.resp.UserResponse;

public interface IAuthService
{

    UserResponse register(FormRegister formRegister);

    JwtResponse login(FormLogin formLogin);

}
