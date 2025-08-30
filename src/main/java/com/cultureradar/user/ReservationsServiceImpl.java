package com.cultureradar.user;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ReservationsServiceImpl implements ReservationsService {

    private final Map<String, Set<String>> storage = new ConcurrentHashMap<>();

    @Override
    public List<String> listReservations(String userId) {
        return new ArrayList<>(storage.getOrDefault(userId, Collections.emptySet()));
    }

    @Override
    public void addReservation(String userId, String eventUid) {
        storage.computeIfAbsent(userId, k -> ConcurrentHashMap.newKeySet())
                .add(eventUid);
    }

    @Override
    public void removeReservation(String userId, String eventUid) {
        storage.getOrDefault(userId, Collections.emptySet())
                .remove(eventUid);
    }
}
