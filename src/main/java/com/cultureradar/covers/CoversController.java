package com.cultureradar.covers;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/covers")
@CrossOrigin(origins = {
        "http://localhost:3001",
        "https://ias-b3-2-paris-g8.fr"
})
public class CoversController {

    private final CoversService service;

    public CoversController(CoversService service) {
        this.service = service;
    }

    @GetMapping("/auto")
    public Map<String, String> auto(@RequestParam String uid, @RequestParam String q) {
        String url = service.getAutoCoverUrl(uid, q);
        return url != null ? Map.of("url", url) : Map.of();
    }
}
