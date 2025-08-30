package com.cultureradar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class AnalyticsConfig {
    @Value("${ga4.measurementId:}")
    private String measurementId;

    @Value("${ga4.apiSecret:}")
    private String apiSecret;

    public String getMeasurementId() { return measurementId; }
    public String getApiSecret() { return apiSecret; }

    @PostConstruct
    void logIfSet() {
        System.out.println("[GA4] measurementId défini ? " + (measurementId != null && !measurementId.isBlank())
                + " | apiSecret défini ? " + (apiSecret != null && !apiSecret.isBlank()));
    }
}

