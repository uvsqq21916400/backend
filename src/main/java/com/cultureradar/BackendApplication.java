package com.cultureradar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.cultureradar")   // scan des interfaces JpaRepository (ex: UserFavoriteRepo)
@EntityScan(basePackages = "com.cultureradar")               // scan des entités JPA (ex: UserFavorite)
@ConfigurationPropertiesScan(basePackages = "com.cultureradar") // scan des *Properties (OpenAgendaProperties, UnsplashProperties…)
public class BackendApplication {
    private static final Logger log = LoggerFactory.getLogger(BackendApplication.class);
    public BackendApplication(Environment env) {
        log.info("SPRING_DATASOURCE_URL={}", env.getProperty("SPRING_DATASOURCE_URL"));
        log.info("spring.datasource.url={}", env.getProperty("spring.datasource.url"));
    }
    public static void main(String[] args) { SpringApplication.run(BackendApplication.class, args); }
}

