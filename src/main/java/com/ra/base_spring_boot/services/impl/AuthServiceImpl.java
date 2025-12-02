package com.ra.base_spring_boot.services.impl;

import com.ra.base_spring_boot.dto.req.FormLogin;
import com.ra.base_spring_boot.dto.req.FormRegister;
import com.ra.base_spring_boot.dto.resp.JwtResponse;
import com.ra.base_spring_boot.dto.resp.UserResponse;
import com.ra.base_spring_boot.exception.HttpBadRequest;
import com.ra.base_spring_boot.exception.HttpConflict;
import com.ra.base_spring_boot.model.BlackListToken;
import com.ra.base_spring_boot.model.Role;
import com.ra.base_spring_boot.model.User;
import com.ra.base_spring_boot.model.constants.RoleName;
import com.ra.base_spring_boot.model.constants.UserStatus;
import com.ra.base_spring_boot.repository.IBlackListToken;
import com.ra.base_spring_boot.repository.IUserRepository;
import com.ra.base_spring_boot.security.jwt.JwtProvider;
import com.ra.base_spring_boot.security.principle.MyUserDetails;
import com.ra.base_spring_boot.services.IAuthService;
import com.ra.base_spring_boot.services.IRoleService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService
{
    private final IRoleService roleService;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final IBlackListToken iBlackListToken;

    @Override
    public UserResponse register(FormRegister formRegister)
    {
        if(userRepository.existsByEmail(formRegister.getEmail())){
            throw new HttpConflict("This email is already in use.");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        User user = User.builder()
                .firstName(formRegister.getFirstName())
                .lastName(formRegister.getLastName())
                .email(formRegister.getEmail())
                .password(passwordEncoder.encode(formRegister.getPassword()))
                .phone(formRegister.getPhone())
                .status(UserStatus.ACTIVE)
                .roles(roles)
                .build();
        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }

    @Override
    public JwtResponse login(FormLogin formLogin)
    {
        //Xóa toàn bộ token đã logout trước khi login ngan
        deleteTokenFromBlackList();

        Authentication authentication;
        try
        {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.getEmail(), formLogin.getPassword()));
        }
        catch (AuthenticationException e)
        {
            throw new HttpBadRequest("Email or password is incorrect");
        }

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        if (userDetails.getUser().getStatus() == UserStatus.BLOCKED)
        {
            throw new HttpBadRequest("Your account is blocked");
        }


        // Tạo token mới ngan
        String newToken = jwtProvider.generateToken(userDetails.getUsername());


        return JwtResponse.builder()
                .accessToken(jwtProvider.generateToken(userDetails.getUsername()))
                .user(new UserResponse(userDetails.getUser()))
                .build();
    }

    @Override
    public BlackListToken logout(String token) {

        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("token is null or empty");
        }
        boolean isExists = iBlackListToken.existsByToken(token);
        if (!isExists) {
            BlackListToken blackListToken = new BlackListToken();
            blackListToken.setToken(token);
            blackListToken.setLogin(false);
            blackListToken.setCreatedAt(LocalDateTime.now());
            return iBlackListToken.save(blackListToken);
        } else {
            throw new RuntimeException("Token is already in blacklist");
        }
    }

    @Override
    public boolean isTokenInBlackList(String token) {
        return iBlackListToken.existsByToken(token);
    }

    @Override
    public void deleteTokenFromBlackList() {
        iBlackListToken.deleteAllByIsLoginFalse();
        System.out.println("Đã xóa toàn bộ các token có isLogin = false trong blackList");
    }





}
