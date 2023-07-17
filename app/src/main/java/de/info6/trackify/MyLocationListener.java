package de.info6.trackify;

import android.location.Location;
import android.location.LocationListener;
import android.util.Log;

import androidx.annotation.NonNull;

public class MyLocationListener implements LocationListener {

    static double longitude, latitude;

    @Override
    public void onLocationChanged(Location loc) {
        longitude = loc.getLongitude();
        latitude = loc.getLatitude();

        Log.d("Koordinaten", "Latitude: " + latitude);

        Log.d("Koordinaten", "Longitude: " + longitude);

    }
}
