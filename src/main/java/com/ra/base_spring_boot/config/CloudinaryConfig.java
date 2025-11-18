package com.ra.base_spring_boot.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    private static final String CLOUD_NAME = "dyvkvylck";
    private static final String API_KEY = "378534662522117";
    private static final String API_SECRET = "aMt5gNVlcyBRKlKf4HJslwSrDqM";

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap("cloud_name", CLOUD_NAME, "api_key", API_KEY, "api_secret", API_SECRET));
    }
}