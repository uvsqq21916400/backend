package com.cultureradar.user;

import java.util.List;

public interface ReservationsService {
    List<String> listReservations(String userId);
    void addReservation(String userId, String eventUid);
    void removeReservation(String userId, String eventUid);
}
