package com.ra.base_spring_boot.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JwtResponse
{
    private String accessToken;
    private final String type = "Bearer";
    @JsonIgnoreProperties({"password"})
    private UserResponse user;
}
