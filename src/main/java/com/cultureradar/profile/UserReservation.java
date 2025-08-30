package com.cultureradar.profile;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "user_reservations")
public class UserReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "event_uid", nullable = false)
    private String eventUid;

    private OffsetDateTime timingBegin; // créneau choisi (optionnel)

    private Integer priceCents; // si tu veux afficher un prix indicatif

    @Column(nullable = false)
    private String status = "CONFIRMED"; // CONFIRMED | CANCELLED

    @Column(columnDefinition = "jsonb", nullable = false)
    private String snapshot;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getEventUid() { return eventUid; }
    public void setEventUid(String eventUid) { this.eventUid = eventUid; }
    public OffsetDateTime getTimingBegin() { return timingBegin; }
    public void setTimingBegin(OffsetDateTime timingBegin) { this.timingBegin = timingBegin; }
    public Integer getPriceCents() { return priceCents; }
    public void setPriceCents(Integer priceCents) { this.priceCents = priceCents; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getSnapshot() { return snapshot; }
    public void setSnapshot(String snapshot) { this.snapshot = snapshot; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
