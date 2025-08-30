package com.cultureradar.openagenda;

import com.cultureradar.openagenda.dto.OaEventList;
import com.cultureradar.openagenda.geo.GeoBox;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Service
public class OpenAgendaService {
    private final WebClient client;
    private final OpenAgendaProperties props;

    public OpenAgendaService(@Qualifier("openAgendaWebClient") WebClient client,
                             OpenAgendaProperties props) {
        this.client = client;
        this.props = props;
    }

    /** Liste simple des événements à venir (tri chronologique) */
    public OaEventList listUpcoming(Integer size) {
        MultiValueMap<String, String> q = baseQuery(size);

        String path = "/agendas/" + props.agendaUid() + "/events";
        return client.get()
                .uri(uri -> uri.path(path).queryParam("lang", "fr").queryParams(q).build())
                .retrieve()
                .bodyToMono(OaEventList.class)
                .block();
    }

    /** Événements autour d’un point (bbox) avec période optionnelle J+days */
    public OaEventList listNearby(double lat, double lng, double radiusKm,
                                  Integer size, Integer days) {
        GeoBox box = GeoBox.fromCenterRadiusKm(lat, lng, radiusKm);

        MultiValueMap<String, String> q = baseQuery(size);
        // filtre géographique (bbox)
        q.add("geo[northEast][lat]", String.valueOf(box.neLat()));
        q.add("geo[northEast][lng]", String.valueOf(box.neLng()));
        q.add("geo[southWest][lat]", String.valueOf(box.swLat()));
        q.add("geo[southWest][lng]", String.valueOf(box.swLng()));

        // borne temporelle en UTC si days est fourni
        if (days != null && days > 0) {
            OffsetDateTime nowUtc = OffsetDateTime.now(ZoneOffset.UTC);
            OffsetDateTime toUtc  = nowUtc.plusDays(days);
            q.add("timings[gte]", nowUtc.toString()); // ISO-8601 (ex: 2025-08-21T00:00:00Z)
            q.add("timings[lte]", toUtc.toString());
        }

        String path = "/agendas/" + props.agendaUid() + "/events";
        return client.get()
                .uri(uri -> uri.path(path).queryParam("lang", "fr").queryParams(q).build())
                .retrieve()
                .bodyToMono(OaEventList.class)
                .block();
    }

    /** Récupérer un évènement par UID (pour la page détail) */
    public Map<String, Object> getByUid(String uid) {
        String path = "/agendas/" + props.agendaUid() + "/events/" + uid;
        // detailed=1 pour avoir description, images, etc.
        return client.get()
                .uri(uri -> uri.path(path)
                        .queryParam("lang", "fr")
                        .queryParam("detailed", "1")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    /** Paramètres communs (tri + taille + champs utiles, incluant toutes variantes d’images) */
    private MultiValueMap<String, String> baseQuery(Integer size) {
        MultiValueMap<String, String> q = new LinkedMultiValueMap<>();
        q.add("relative[]", "upcoming");
        q.add("sort", "timings.asc");
        q.add("size", String.valueOf(size != null ? size : 50));

        // Champs minimaux (côté front on affiche titre, timings, lieu…)
        q.add("if[]", "uid");
        q.add("if[]", "title.fr");
        q.add("if[]", "timings");
        q.add("if[]", "location");

        // IMAGES — couvrir toutes les formes possibles renvoyées par OpenAgenda
        q.add("if[]", "thumbnail");
        q.add("if[]", "image");
        q.add("if[]", "image.url");
        q.add("if[]", "images");
        q.add("if[]", "images.url");

        // Catégorie / mots-clés (pour le badge sur la carte)
        q.add("if[]", "keywords.label.fr");

        return q;
    }
}


