package com.cultureradar.covers;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(UnsplashProperties.class)
public class UnsplashConfig {

    @Bean
    public WebClient unsplashWebClient(WebClient.Builder builder, UnsplashProperties props) {
        return builder
                .baseUrl("https://api.unsplash.com")
                .defaultHeader("Authorization", "Client-ID " + (props.accessKey() == null ? "" : props.accessKey()))
                .build();
    }
}
