package com.cultureradar.profile;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(
        name = "user_favorites",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "event_uid"})
)
public class UserFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "event_uid", nullable = false)
    private String eventUid;

    // on stocke un petit "snapshot" JSON de l'évènement pour l'affichage rapide
    @Column(columnDefinition = "jsonb", nullable = false)
    private String snapshot;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getEventUid() { return eventUid; }
    public void setEventUid(String eventUid) { this.eventUid = eventUid; }
    public String getSnapshot() { return snapshot; }
    public void setSnapshot(String snapshot) { this.snapshot = snapshot; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
