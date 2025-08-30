package com.cultureradar.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface UserReservationRepo extends JpaRepository<UserReservation, Long> {
    List<UserReservation> findByUserIdOrderByCreatedAtDesc(String userId);
    Optional<UserReservation> findByIdAndUserId(Long id, String userId);
}
