package com.cultureradar.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/reservations")
@CrossOrigin(
        origins = {"http://localhost:3001", "http://127.0.0.1:3001"},
        allowCredentials = "true"
)
public class ReservationsController {

    private final ReservationsService service;

    public ReservationsController(ReservationsService service) {
        this.service = service;
    }

    @GetMapping
    public List<String> list(@PathVariable String userId) {
        return service.listReservations(userId);
    }

    @PostMapping("/{eventUid}")
    public ResponseEntity<Void> add(@PathVariable String userId,
                                    @PathVariable String eventUid) {
        service.addReservation(userId, eventUid);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{eventUid}")
    public ResponseEntity<Void> remove(@PathVariable String userId,
                                       @PathVariable String eventUid) {
        service.removeReservation(userId, eventUid);
        return ResponseEntity.noContent().build();
    }
}
