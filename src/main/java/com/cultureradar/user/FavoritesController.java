// src/main/java/com/cultureradar/user/FavoritesController.java
package com.cultureradar.user;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users/{userId}/favorites")
public class FavoritesController {
    private final FavoritesService service;

    public FavoritesController(FavoritesService service) {
        this.service = service;
    }

    /** GET /api/users/{userId}/favorites -> [ "uid1", "uid2", ... ] */
    @GetMapping
    public List<String> list(@PathVariable String userId) {
        return service.listFavorites(userId);
    }

    /** POST /api/users/{userId}/favorites  body: { "uid": "xxx" } -> liste maj */
    @PostMapping
    public List<String> add(@PathVariable String userId, @RequestBody Map<String,String> body) {
        String uid = body.get("uid");
        return service.addFavorite(userId, uid);
    }

    /** DELETE /api/users/{userId}/favorites/{uid} -> liste maj */
    @DeleteMapping("/{uid}")
    public List<String> remove(@PathVariable String userId, @PathVariable String uid) {
        return service.removeFavorite(userId, uid);
    }
}




