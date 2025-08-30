package com.cultureradar.covers;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "unsplash")
public record UnsplashProperties(String accessKey) {}
