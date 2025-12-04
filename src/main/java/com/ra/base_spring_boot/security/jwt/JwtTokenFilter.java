package com.ra.base_spring_boot.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.base_spring_boot.repository.IBlackListToken;
import com.ra.base_spring_boot.security.principle.MyUserDetails;
import com.ra.base_spring_boot.security.principle.MyUserDetailsService;
import com.ra.base_spring_boot.services.IAuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter
{
    private final MyUserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;
    private final IBlackListToken blackListTokenRepo;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException
    {
        try
        {
            String token = getTokenFromRequest(request);
            if (token != null)
            {
                String username = jwtProvider.extractUsername(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtProvider.validateToken(token, userDetails) )
                {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                String path = request.getRequestURI();
                boolean isAuthEndpoint = path.startsWith("/api/v1/auth/");

            // Kiểm tra token blacklist chỉ với endpoint cần bảo vệ
                if (!isAuthEndpoint && blackListTokenRepo.existsByToken(token)) {
                    SecurityContextHolder.clearContext();
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    Map<String, Object> errors = new HashMap<>();
                    errors.put("code", 401);
                    errors.put("error", "Full authentication is required to access this resource");
                    new ObjectMapper().writeValue(response.getOutputStream(), errors);
                    return;
                }
                //refresh token
               boolean isRefreshEndpoint = request.getRequestURI().equals("/api/v1/refresh/");
                if (!isAuthEndpoint && !isRefreshEndpoint) {
                    Date expiration = jwtProvider.extractExpiration(token);
                    if (expiration.before(new Date())) {
                        SecurityContextHolder.clearContext();
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                        return;
                    }
                }


                // Kiểm tra token expired chỉ với endpoint cần bảo vệ
//                if (!isAuthEndpoint) {
//                    Date expiration = jwtProvider.extractExpiration(token);
//                    if (expiration.before(new Date())) {
//                        SecurityContextHolder.clearContext();
//                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
//                        return;
//                    }
//                }

            }
        }
        catch (Exception e)
        {
            log.error("Un Authentication {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    public String getTokenFromRequest(HttpServletRequest request)
    {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer "))
        {
            return header.substring(7);
        }
        return null;
    }
}