// src/main/java/com/cultureradar/user/FavoritesService.java
package com.cultureradar.user;

import java.util.List;

public interface FavoritesService {
    List<String> listFavorites(String userId);
    List<String> addFavorite(String userId, String uid);
    List<String> removeFavorite(String userId, String uid);
}





