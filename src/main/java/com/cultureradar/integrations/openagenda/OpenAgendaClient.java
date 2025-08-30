package com.cultureradar.integrations.openagenda;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OpenAgendaClient {
    private final String apiKey;

    public OpenAgendaClient(@Value("${openagenda.apiKey:}") String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean hasKey() {
        return apiKey != null && !apiKey.isBlank();
    }

    public String getApiKey() {
        return apiKey;
    }
}

