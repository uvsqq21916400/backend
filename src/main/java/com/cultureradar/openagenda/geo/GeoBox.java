package com.cultureradar.openagenda.geo;

public record GeoBox(double neLat, double neLng, double swLat, double swLng) {
    public static GeoBox fromCenterRadiusKm(double lat, double lng, double radiusKm) {
        double latDelta = radiusKm / 111.0;
        double lngDelta = radiusKm / (111.0 * Math.cos(Math.toRadians(lat)));
        return new GeoBox(lat + latDelta, lng + lngDelta, lat - latDelta, lng - lngDelta);
    }
}
