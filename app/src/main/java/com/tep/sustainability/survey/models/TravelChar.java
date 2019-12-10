package com.tep.sustainability.survey.models;

import com.google.android.gms.maps.model.LatLng;

public class TravelChar {
    private String purpose;
    private String mode;
    private String startTime;
    private String endTime;
    private String startLoc;
    private String endLoc;
    private float travelTime;
    private float startLat;
    private float startLong;
    private float endLat;
    private float endLong;
    private float waitTime;
    private float vehicleTime;
    private float distance;
    private float parking;
    private String frequency;

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartLoc() {
        return startLoc;
    }

    public void setStartLoc(String startLoc) {
        this.startLoc = startLoc;
    }

    public String getEndLoc() {
        return endLoc;
    }

    public void setEndLoc(String endLoc) {
        this.endLoc = endLoc;
    }

    public float getStartLat() {
        return startLat;
    }

    public void setStartLat(float startLat) {
        this.startLat = startLat;
    }

    public float getStartLong() {
        return startLong;
    }

    public void setStartLong(float startLong) {
        this.startLong = startLong;
    }

    public float getEndLat() {
        return endLat;
    }

    public void setEndLat(float endLat) {
        this.endLat = endLat;
    }

    public float getEndLong() {
        return endLong;
    }

    public void setEndLong(float endLong) {
        this.endLong = endLong;
    }

    public float getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(float waitTime) {
        this.waitTime = waitTime;
    }

    public float getVehicleTime() {
        return vehicleTime;
    }

    public void setVehicleTime(float vehicleTime) {
        this.vehicleTime = vehicleTime;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setStartLocation(LatLng latLng) {
        this.setStartLat((float) latLng.latitude);
        this.setStartLong((float) latLng.longitude);
    }

    public void setEndLocation(LatLng latLng) {
        this.setEndLat((float) latLng.latitude);
        this.setEndLong((float) latLng.longitude);
    }

    public float getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(float travelTime) {
        this.travelTime = travelTime;
    }

    public float getParking() {
        return parking;
    }

    public void setParking(float parking) {
        this.parking = parking;
    }
}
