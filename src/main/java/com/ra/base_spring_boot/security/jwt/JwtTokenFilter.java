package com.ra.base_spring_boot.security.jwt;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter
{
    private final MyUserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;
    private final IBlackListToken blackListTokenRepo;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
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
                // kiểm tra xem token có trong blackList không
                if(blackListTokenRepo.existsByToken(token)){
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "tài khoản đã logout, vui lòng đăng nhập lại.");
                    return;
                }
                // giải mã và lấy email từ subject trong token
                String email = jwtProvider.getSubjectFromToken(token);

                Date expiration = jwtProvider.extractExpiration(token);
                if(expiration.before(new Date())) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token expired");
                }

                // tìm ra đối tượng UserDetails

                UserDetails myUserDetails = userDetailsService.loadUserByUsername(email);

                //Xác thực thông qua UserDetails tạo ra đối tượng Authentication
                Authentication authentication = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());

                // lưu vào securityContextHolder

                SecurityContextHolder.getContext().setAuthentication(authentication);

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
