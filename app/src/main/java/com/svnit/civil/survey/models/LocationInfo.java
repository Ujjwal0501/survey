package com.svnit.civil.survey.models;

import android.location.Location;
import android.os.Build;

public class LocationInfo {
    private Double longitude;
    private Double latitude;
    private Double speed;
    private Double altitude;
    private Double speedAccuracy;
    private Double locationAccuracy;
    private Double altitudeAccuracy;
    private Long time;

    public void LocationInfo() {
        this.setLongitude(0D);
        this.setLatitude(0D);
        this.setSpeed(0D);
        this.setAltitude(0D);
        this.setSpeedAccuracy(0D);
        this.setLocationAccuracy(0D);
        this.setAltitudeAccuracy(0D);
        this.setTime(0L);
    }

    public void LocationInfo(Double longitude, Double latitude, Double speed, Double altitude, Long time,
                             Double speedAccuracy, Double locationAccuracy, Double altitudeAccuracy) {
        this.setLongitude(longitude);
        this.setLatitude(latitude);
        this.setSpeed(speed);
        this.setAltitude(altitude);
        this.setSpeedAccuracy(speedAccuracy);
        this.setLocationAccuracy(locationAccuracy);
        this.setAltitudeAccuracy(altitudeAccuracy);
        this.setTime(time);
    }

    public void LocationInfo(Location location) {
        this.setLongitude(location.getLongitude());
        this.setLatitude(location.getLatitude());
        this.setSpeed(1D * location.getSpeed());
        this.setAltitude(location.getAltitude());
        if (Build.VERSION.SDK_INT > 25 && location.hasSpeedAccuracy())
            this.setSpeedAccuracy(1D * location.getSpeedAccuracyMetersPerSecond());
        else
            this.setSpeedAccuracy(0D);
        if (location.hasAccuracy())
            this.setLocationAccuracy(1D * location.getAccuracy());
        else
            this.setLocationAccuracy(0D);
        if (Build.VERSION.SDK_INT > 25 && location.hasVerticalAccuracy())
            this.setAltitudeAccuracy(1D * location.getVerticalAccuracyMeters());
        else
            this.setAltitudeAccuracy(0D);
        this.setTime(location.getTime());
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getSpeedAccuracy() {
        return speedAccuracy;
    }

    public void setSpeedAccuracy(Double speedAccuracy) {
        this.speedAccuracy = speedAccuracy;
    }

    public Double getLocationAccuracy() {
        return locationAccuracy;
    }

    public void setLocationAccuracy(Double locationAccuracy) {
        this.locationAccuracy = locationAccuracy;
    }

    public Double getAltitudeAccuracy() {
        return altitudeAccuracy;
    }

    public void setAltitudeAccuracy(Double altitudeAccuracy) {
        this.altitudeAccuracy = altitudeAccuracy;
    }

    public Long getTime() { return time; }

    public void setTime(Long time) { this.time = time; }

    @Override
    public String toString() {
        String str = "";
        str += "time:"+time.toString()+"\n";
        str += "longitude:"+longitude.toString()+"\n";
        str += "latitude:"+latitude.toString()+"\n";
        str += "speed:"+speed.toString()+"\n";
        str += "locationAccuracy:"+locationAccuracy.toString()+"\n";
        return str;
    }
}
