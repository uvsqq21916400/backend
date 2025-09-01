package com.cultureradar.openagenda;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "apis.openagenda")
public record OpenAgendaProperties(
        String baseUrl,
        String publicKey,   // <-- NOM ATTENDU PAR TON CODE
        String agendaUid
) {}

