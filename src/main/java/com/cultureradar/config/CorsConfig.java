// src/main/java/com/cultureradar/config/CorsConfig.java
package com.cultureradar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration c = new CorsConfiguration();

        // Origins autorisés (pas de wildcard si credentials=true)
        c.setAllowedOrigins(Arrays.asList(
                "http://localhost:3001",
                "http://127.0.0.1:3001",
                "https://ias-b3-2-paris-g8.fr"   // prod (à ajuster si besoin)
        ));

        // Méthodes et en-têtes
        c.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        c.setAllowedHeaders(Collections.singletonList("*"));

        // Si votre front envoie credentials: "include" -> doit être true
        c.setAllowCredentials(true);

        // (optionnel) exposer certains headers au front
        // c.setExposedHeaders(Arrays.asList("Location"));

        c.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", c);
        return source;
    }
}




