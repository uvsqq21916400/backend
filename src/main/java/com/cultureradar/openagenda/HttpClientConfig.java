package com.cultureradar.openagenda;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(OpenAgendaProperties.class)
public class HttpClientConfig {

    @Bean(name = "openAgendaWebClient") // <— nouveau nom
    public WebClient openAgendaWebClient(OpenAgendaProperties props) {
        return WebClient.builder()
                .baseUrl("https://api.openagenda.com/v2")
                .defaultHeader("key", props.publicKey())
                .build();
    }
}

