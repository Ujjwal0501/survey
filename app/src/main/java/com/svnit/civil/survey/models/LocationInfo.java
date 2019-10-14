package com.svnit.civil.survey.models;

import android.location.Location;
import android.os.Build;

public class LocationInfo {
    private String longitude;
    private String latitude;
    private String speed;
    private String altitude;
    private String speedAccuracy;
    private String locationAccuracy;
    private String altitudeAccuracy;

    public void LocationInfo() {
        this.setLongitude("");
        this.setLatitude("");
        this.setSpeed("");
        this.setAltitude("");
        this.setSpeedAccuracy("");
        this.setLocationAccuracy("");
        this.setAltitudeAccuracy("");
    }

    public void LocationInfo(String longitude, String latitude, String speed, String altitude,
                             String speedAccuracy, String locationAccuracy, String altitudeAccuracy) {
        this.setLongitude("");
        this.setLatitude("");
        this.setSpeed("");
        this.setAltitude("");
        this.setSpeedAccuracy("");
        this.setLocationAccuracy("");
        this.setAltitudeAccuracy("");
    }

    public void LocationInfo(Location location) {
        this.setLongitude("" + location.getLongitude());
        this.setLatitude(Location.convert(location.getLongitude(), Location.FORMAT_DEGREES));
        this.setSpeed("" + location.getSpeed());
        this.setAltitude("" + location.getAltitude());
        if (Build.VERSION.SDK_INT > 25 && location.hasSpeedAccuracy())
            this.setSpeedAccuracy("" + location.getSpeedAccuracyMetersPerSecond());
        else
            this.setSpeedAccuracy("No");
        if (location.hasAccuracy())
            this.setLocationAccuracy("" + location.getAccuracy());
        else
            this.setLocationAccuracy("No");
        if (Build.VERSION.SDK_INT > 25 && location.hasVerticalAccuracy())
            this.setAltitudeAccuracy("" + location.getVerticalAccuracyMeters());
        else
            this.setAltitudeAccuracy("No");
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getSpeedAccuracy() {
        return speedAccuracy;
    }

    public void setSpeedAccuracy(String speedAccuracy) {
        this.speedAccuracy = speedAccuracy;
    }

    public String getLocationAccuracy() {
        return locationAccuracy;
    }

    public void setLocationAccuracy(String locationAccuracy) {
        this.locationAccuracy = locationAccuracy;
    }

    public String getAltitudeAccuracy() {
        return altitudeAccuracy;
    }

    public void setAltitudeAccuracy(String altitudeAccuracy) {
        this.altitudeAccuracy = altitudeAccuracy;
    }
}
