package com.cultureradar.openagenda;

import com.cultureradar.openagenda.dto.OaEventList;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/events")
// Si vous avez déjà un CorsConfig global, vous pouvez retirer @CrossOrigin ici.
@CrossOrigin(origins = {
        "http://localhost:3001",         // front en dev (si vous servez en 3001)
        "http://localhost:3000",         // (optionnel) si vous utilisez 3000 parfois
        "https://ias-b3-2-paris-g8.fr"   // votre domaine prod (à ajuster)
})
public class OpenAgendaController {

    private final OpenAgendaService service;

    public OpenAgendaController(OpenAgendaService service) {
        this.service = service;
    }

    /** Liste simple des évènements à venir (triés chronologiquement) */
    @GetMapping("/upcoming")
    public OaEventList upcoming(@RequestParam(required = false) Integer size) {
        return service.listUpcoming(size);
    }

    /** Évènements autour d’un point (lat,lng) dans un rayon (km), période optionnelle J+days */
    @GetMapping("/nearby")
    public OaEventList nearby(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "15") double radiusKm,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer days
    ) {
        return service.listNearby(lat, lng, radiusKm, size, days);
    }

    /** Détail d’un évènement par UID (pour la page /evenements/:uid) */
    @GetMapping("/{uid}")
    public Map<String, Object> byUid(@PathVariable String uid) {
        return service.getByUid(uid);
    }
}


