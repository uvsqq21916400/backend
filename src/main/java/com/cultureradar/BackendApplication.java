package com.cultureradar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.cultureradar")   // scan des interfaces JpaRepository (ex: UserFavoriteRepo)
@EntityScan(basePackages = "com.cultureradar")               // scan des entités JPA (ex: UserFavorite)
@ConfigurationPropertiesScan(basePackages = "com.cultureradar") // scan des *Properties (OpenAgendaProperties, UnsplashProperties…)
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}

