// src/main/java/com/app/openagenda/OpenAgendaProperties.java
package com.cultureradar.openagenda;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "openagenda")
public record OpenAgendaProperties(String publicKey, String agendaUid) {}
