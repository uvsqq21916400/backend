package com.cultureradar.profile;

import com.cultureradar.openagenda.OpenAgendaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MeService {

    private final UserFavoriteRepo favRepo;
    private final UserReservationRepo resRepo;
    private final OpenAgendaService oa;
    private final ObjectMapper om;

    public MeService(UserFavoriteRepo favRepo,
                     UserReservationRepo resRepo,
                     OpenAgendaService oa,
                     ObjectMapper om) {
        this.favRepo = favRepo;
        this.resRepo = resRepo;
        this.oa = oa;
        this.om = om;
    }

    private String snapshotFor(String uid) throws Exception {
        Map<String, Object> ev = oa.getByUid(uid); // méthode déjà ajoutée dans ton OA service
        return om.writeValueAsString(ev);
    }

    // -------- favoris ----------
    public List<UserFavorite> listFav(String userId) {
        return favRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public UserFavorite addFav(String userId, String uid) throws Exception {
        return favRepo.findByUserIdAndEventUid(userId, uid).orElseGet(() -> {
            try {
                UserFavorite f = new UserFavorite();
                f.setUserId(userId);
                f.setEventUid(uid);
                f.setSnapshot(snapshotFor(uid));
                return favRepo.save(f);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void removeFav(String userId, String uid) {
        favRepo.deleteByUserIdAndEventUid(userId, uid);
    }

    // -------- réservations ----------
    public List<UserReservation> listRes(String userId) {
        return resRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public UserReservation book(String userId, String uid, OffsetDateTime when, Integer priceCents) throws Exception {
        UserReservation r = new UserReservation();
        r.setUserId(userId);
        r.setEventUid(uid);
        r.setTimingBegin(when);
        r.setPriceCents(priceCents);
        r.setStatus("CONFIRMED");
        r.setSnapshot(snapshotFor(uid));
        return resRepo.save(r);
    }

    public UserReservation cancel(String userId, Long id) {
        UserReservation r = resRepo.findByIdAndUserId(id, userId).orElseThrow();
        r.setStatus("CANCELLED");
        return resRepo.save(r);
    }
}
