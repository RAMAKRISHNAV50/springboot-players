package com.example.players; // or com.example.franchise

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all endpoints
                .allowedOrigins("https://cric-auction-liart.vercel.app") // Allow Vite
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow all CRUD + Preflight
                .allowedHeaders("*") // Allow all headers (Content-Type, etc.)
                .allowCredentials(true);
    }
}