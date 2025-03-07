package com.example.calculator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true); // Allow cookies/credentials
    config.addAllowedOrigin("http://localhost:8080"); // Your frontend origin
    config.addAllowedHeader("*"); // Allow all headers
    config.addAllowedMethod("*"); // Allow all methods (GET, POST, OPTIONS, etc.)
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}