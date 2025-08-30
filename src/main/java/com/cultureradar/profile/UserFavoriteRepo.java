package com.cultureradar.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface UserFavoriteRepo extends JpaRepository<UserFavorite, Long> {
    List<UserFavorite> findByUserIdOrderByCreatedAtDesc(String userId);
    Optional<UserFavorite> findByUserIdAndEventUid(String userId, String eventUid);
    void deleteByUserIdAndEventUid(String userId, String eventUid);
}
