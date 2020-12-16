package com.example.travelplanner_0_1_1.animation;

import com.google.android.gms.maps.model.LatLng;

public interface LatLngInterpolator {
    LatLng interpolate(float fraction, LatLng a, LatLng b);

    class Linear implements LatLngInterpolator {
        @Override
        public LatLng interpolate(float fraction, LatLng a, LatLng b) {
            double lat = (b.latitude - a.latitude) * fraction + a.latitude;
            double lng = (b.longitude - a.longitude) * fraction + a.longitude;
            return new LatLng(lat, lng);
        }
    }
}