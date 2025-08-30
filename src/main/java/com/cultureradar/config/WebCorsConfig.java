// src/main/java/com/cultureradar/config/WebCorsConfig.java
package com.cultureradar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry r) {
                r.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3001", "http://127.0.0.1:3001")
                        .allowedMethods("GET","POST","DELETE","PUT","PATCH","OPTIONS")
                        .allowCredentials(true);
            }
        };
    }
}
