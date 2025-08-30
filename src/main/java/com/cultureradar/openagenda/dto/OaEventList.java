package com.cultureradar.openagenda.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OaEventList(
        Integer total,
        List<Map<String, Object>> events,
        Map<String, Object> pagination
) {}
