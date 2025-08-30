package com.cultureradar.covers;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CoversService {

    private final WebClient unsplash;
    private final UnsplashProperties props;

    // cache {uid -> (url, expireAt)}
    private final ConcurrentHashMap<String, Entry> cache = new ConcurrentHashMap<>();
    // durée de cache (ex: 30 jours)
    private static final Duration TTL = Duration.ofDays(30);

    public CoversService(WebClient unsplashWebClient, UnsplashProperties props) {
        this.unsplash = unsplashWebClient;
        this.props = props;
    }

    public String getAutoCoverUrl(String uid, String query) {
        if (!StringUtils.hasText(uid)) return null;

        // 1) cache
        Entry e = cache.get(uid);
        if (e != null && Instant.now().isBefore(e.expiresAt())) {
            return e.url();
        }

        // si pas de clé → on ne tente pas
        if (!StringUtils.hasText(props.accessKey())) {
            return null;
        }

        // 2) appel Unsplash
        String q = (StringUtils.hasText(query) ? query : "évènement culturel");
        String encoded = URLEncoder.encode(q, StandardCharsets.UTF_8);

        // /search/photos?query=...&orientation=landscape&per_page=1&content_filter=high
        Map<String, Object> res = unsplash.get()
                .uri(uri -> uri.path("/search/photos")
                        .queryParam("query", encoded) // encodage ici OK
                        .queryParam("orientation", "landscape")
                        .queryParam("per_page", "1")
                        .queryParam("content_filter", "high")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        String bestUrl = extractFirstUrl(res);
        if (bestUrl != null) {
            cache.put(uid, new Entry(bestUrl, Instant.now().plus(TTL)));
        }
        return bestUrl;
    }

    @SuppressWarnings("unchecked")
    private String extractFirstUrl(Map<String, Object> search) {
        if (search == null) return null;
        Object resultsObj = search.get("results");
        if (!(resultsObj instanceof List<?> results) || results.isEmpty()) return null;

        Object first = results.get(0);
        if (!(first instanceof Map<?, ?> f)) return null;

        Object urlsObj = f.get("urls");
        if (!(urlsObj instanceof Map<?, ?> urls)) return null;

        // on tente "regular" puis "small"
        Object regular = urls.get("regular");
        if (regular instanceof String s && StringUtils.hasText(s)) return s;

        Object small = urls.get("small");
        if (small instanceof String s2 && StringUtils.hasText(s2)) return s2;

        return null;
    }

    private record Entry(String url, Instant expiresAt) {}
}
