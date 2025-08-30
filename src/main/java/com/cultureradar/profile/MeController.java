package com.cultureradar.profile;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/me")
@CrossOrigin(
        origins = { "http://localhost:3001", "https://ias-b3-2-paris-g8.fr" },
        allowCredentials = "true"
)
public class MeController {

    private final MeService svc;

    public MeController(MeService svc) {
        this.svc = svc;
    }

    // Ici on récupère l'identité depuis un header simple "X-User-Id"
    // (remplace par ton auth réelle si tu as JWT/Security)
    private String user(HttpServletRequest req) {
        String u = req.getHeader("X-User-Id");
        if (u == null || u.isBlank())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing X-User-Id");
        return u;
    }

    // -------- favoris ----------
    @GetMapping("/favorites")
    public List<UserFavorite> listFav(HttpServletRequest req) {
        return svc.listFav(user(req));
    }

    @PostMapping("/favorites")
    public UserFavorite addFav(HttpServletRequest req, @RequestBody Map<String, String> body) throws Exception {
        return svc.addFav(user(req), body.get("uid"));
    }

    @DeleteMapping("/favorites/{uid}")
    public void removeFav(HttpServletRequest req, @PathVariable String uid) {
        svc.removeFav(user(req), uid);
    }

    // -------- réservations ----------
    @GetMapping("/reservations")
    public List<UserReservation> listRes(HttpServletRequest req) {
        return svc.listRes(user(req));
    }

    @PostMapping("/reservations")
    public UserReservation book(HttpServletRequest req, @RequestBody Map<String, Object> body) throws Exception {
        String uid = (String) body.get("uid");
        String when = (String) body.getOrDefault("when", null);
        Integer priceCents = body.get("priceCents") != null ? ((Number) body.get("priceCents")).intValue() : null;
        OffsetDateTime dt = when != null ? OffsetDateTime.parse(when) : null;
        return svc.book(user(req), uid, dt, priceCents);
    }

    @PatchMapping("/reservations/{id}/cancel")
    public UserReservation cancel(HttpServletRequest req, @PathVariable Long id) {
        return svc.cancel(user(req), id);
    }
}
