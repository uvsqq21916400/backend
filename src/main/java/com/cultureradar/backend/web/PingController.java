package com.cultureradar.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(
        origins = {
                "http://localhost:3001",
                "http://127.0.0.1:3001",
                "https://ias-b3-2-paris-g8.fr",
                "https://www.ias-b3-2-paris-g8.fr"
                // (pas besoin d'ajouter https://api.ias-b3-2-paris-g8.fr :
                // le back accepte toujours les appels depuis lui-même)
        },
        allowCredentials = "true" // utile si tu utilises des cookies/session côté front
)
public class PingController {
    @GetMapping("/api/ping")
    public String ping() {
        return "pong";
    }
}


