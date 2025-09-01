package com.cultureradar.config;

import com.cultureradar.openagenda.OpenAgendaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(OpenAgendaProperties.class)
public class WebClientConfig {

    private static final Logger log = LoggerFactory.getLogger(WebClientConfig.class);

    @Bean(name = "openAgendaWebClient")
    public WebClient openAgendaWebClient(OpenAgendaProperties props) {
        // Valeurs lues
        log.info("[OpenAgenda] baseUrl={}, agendaUid={}", props.baseUrl(), props.agendaUid());

        // Gardes-fous + défaut explicite
        String baseUrl = StringUtils.hasText(props.baseUrl())
                ? props.baseUrl()
                : "https://api.openagenda.com/v2";

        if (!StringUtils.hasText(props.publicKey())) {
            throw new IllegalStateException("OpenAgenda publicKey manquante (apis.openagenda.public-key)");
        }
        if (!StringUtils.hasText(props.agendaUid())) {
            throw new IllegalStateException("OpenAgenda agendaUid manquant (apis.openagenda.agenda-uid)");
        }

        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("key", props.publicKey())
                .build();
    }
}


