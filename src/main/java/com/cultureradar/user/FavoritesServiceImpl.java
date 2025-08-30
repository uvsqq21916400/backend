// src/main/java/com/cultureradar/user/FavoritesServiceImpl.java
package com.cultureradar.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class FavoritesServiceImpl implements FavoritesService {

    // stockage en mémoire : userId -> set d'UID d'évènements
    private final Map<String, LinkedHashSet<String>> store =
            new HashMap<String, LinkedHashSet<String>>();

    @Override
    public List<String> listFavorites(String userId) {
        // on évite Collections.emptySet() ici pour ne pas gêner l'inférence
        Set<String> set = store.get(userId);
        if (set == null) {
            return Collections.emptyList();
        }
        return new ArrayList<String>(set);
    }

    @Override
    public List<String> addFavorite(String userId, String uid) {
        LinkedHashSet<String> set = store.get(userId);
        if (set == null) {
            set = new LinkedHashSet<String>();
            store.put(userId, set);
        }
        set.add(uid);
        return new ArrayList<String>(set);
    }

    @Override
    public List<String> removeFavorite(String userId, String uid) {
        LinkedHashSet<String> set = store.get(userId);
        if (set == null) {
            return Collections.emptyList();
        }
        set.remove(uid);
        return new ArrayList<String>(set);
    }
}


