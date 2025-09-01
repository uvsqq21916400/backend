package com.cultureradar.openagenda;

import com.cultureradar.openagenda.dto.OaEventList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = {
        "http://localhost:3001",
        "http://localhost:3000",
        "https://ias-b3-2-paris-g8.fr"
})
public class OpenAgendaController {

    private final OpenAgendaService service;

    public OpenAgendaController(OpenAgendaService service) {
        this.service = service;
    }

    /** Liste simple des évènements à venir (triés chronologiquement) */
    @GetMapping("/upcoming")
    public ResponseEntity<?> upcoming(@RequestParam(required = false) Integer size) {
        try {
            OaEventList list = service.listUpcoming(size);
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            return ResponseEntity.status(502).body(e.getMessage());
        }
    }

    /** Évènements autour d’un point (lat,lng) dans un rayon (km), période optionnelle J+days */
    @GetMapping("/nearby")
    public ResponseEntity<?> nearby(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "15") double radiusKm,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer days
    ) {
        try {
            OaEventList list = service.listNearby(lat, lng, radiusKm, size, days);
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            return ResponseEntity.status(502).body(e.getMessage());
        }
    }

    /** Endpoint de diagnostic : renvoie le JSON brut d’OpenAgenda */
    @GetMapping("/upcomingRaw")
    public ResponseEntity<String> upcomingRaw(@RequestParam(defaultValue = "3") Integer size) {
        try {
            String json = service.listUpcomingRaw(size);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            return ResponseEntity.status(502).body("OpenAgenda error: " + e.getMessage());
        }
    }

    /** Détail d’un évènement par UID */
    @GetMapping("/{uid}")
    public ResponseEntity<?> getByUid(@PathVariable String uid) {
        try {
            Map<String, Object> event = service.getByUid(uid);
            return ResponseEntity.ok(event);
        } catch (RuntimeException e) {
            return ResponseEntity.status(502).body(e.getMessage());
        }
    }
}
