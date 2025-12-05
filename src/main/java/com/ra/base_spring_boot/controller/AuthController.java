package com.ra.base_spring_boot.controller;

import com.ra.base_spring_boot.dto.ResponseWrapper;
import com.ra.base_spring_boot.dto.req.FormLogin;
import com.ra.base_spring_boot.dto.req.FormRegister;
import com.ra.base_spring_boot.dto.resp.UserResponse;
import com.ra.base_spring_boot.model.BlackListToken;
import com.ra.base_spring_boot.repository.IBlackListToken;
import com.ra.base_spring_boot.security.jwt.JwtProvider;
import com.ra.base_spring_boot.security.jwt.JwtTokenFilter;
import com.ra.base_spring_boot.services.IAuthService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final IAuthService authService;
    private final JwtTokenFilter jwtTokenFilter;
    private final IBlackListToken  blackListToken;
    private final JwtProvider jwtProvider;

    /**
     * @param formLogin FormLogin
     * @apiNote handle login with { email , password }
     */
    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@Valid @RequestBody FormLogin formLogin)
    {
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .status(HttpStatus.OK)
                        .code(200)
                        .data(authService.login(formLogin))
                        .build()
        );
    }

    /**
     * @param formRegister FormRegister
     * @apiNote handle register with { firstName, lastName , email , password, phone }
     */
    @PostMapping("/register")
    public ResponseEntity<?> handleRegister(@Valid @RequestBody FormRegister formRegister)
    {
        UserResponse newUser = authService.register(formRegister);
        return ResponseEntity.created(URI.create("api/v1/auth/register")).body(
                ResponseWrapper.builder()
                        .status(HttpStatus.CREATED)
                        .code(201)
                        .data(newUser)
                        .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServlet){
        String token = jwtTokenFilter.getTokenFromRequest(httpServlet);
        if(token != null){
            if(blackListToken.existsByToken(token)){
                // Kiểm tra nếu token đã trong blacklist
                return ResponseEntity.ok().body("You have logged out");
            }
            // Thêm token vào blacklist
            BlackListToken listToken = BlackListToken.builder()
                    .token(token)
                    .createdAt(LocalDateTime.now())
                    .isLogin(false)   //Đánh dấu là đã logout
                    .build();

            blackListToken.save(listToken);
            return ResponseEntity.ok().body("Log out successfully");
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
        }
    }

    //refresh token
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> req) {
        String refreshToken = req.get("refreshToken");

        try {
            // Giải mã refresh token
            String username = jwtProvider.extractUsername(refreshToken);

            // Tạo access token mới
            String newAccessToken = jwtProvider.generateToken(username);

            return ResponseEntity.ok(
                    ResponseWrapper.builder()
                            .status(HttpStatus.OK)
                            .code(200)
                            .data(Map.of(
                                    "accessToken", newAccessToken,
                                    "refreshToken", refreshToken
                            ))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid refresh token");
        }
    }


}


