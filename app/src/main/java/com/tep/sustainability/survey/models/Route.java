package com.tep.sustainability.survey.models;

import java.util.ArrayList;
import java.util.Date;

public class Route {
    private String purpose;
    private String mode;
    private String startTimeLocal;
    private String endTimeLocal;
    private String startLocation;
    private String endLocation;
    private Double tripLength;
    private Double tripCost;
    private Double distance;
    private Long startTimeMilli;
    private Long endTimeMilli;
    private Long tripFrequency;
    private Long waitTime;
    private Long vehicleTime;
    private Long travelTime;
    private ArrayList<Double> distanceList; // debug

    public Route() {

        this.setPurpose("");
        this.setMode("");
        this.setStartLocation("");
        this.setEndLocation("");

        this.setTripLength(0D);
        this.setTripCost(0D);
        this.setDistance(0D);

        this.setStartTime(0L);
        this.setEndTime(0L);
        this.setTripFrequency(0L);
        this.setWaitTime(0L);
        this.setVehicleTime(0L);
        this.setTravelTime(0L);

        this.distanceList = null;
    }

    public Route(String purpose, String mode, String startLocation, Double tripLength,
                 Double tripCost, String endLocation, Double distance, Long tripFrequency,
                 Long waitTime, Long vehicleTime, Long travelTime, Long startTime, Long endTime) {

        this.setPurpose(purpose);
        this.setMode(mode);

        this.setStartLocation(startLocation);
        this.setTripLength(tripLength);
        this.setTripCost(tripCost);
        this.setEndLocation(endLocation);
        this.setDistance(distance);

        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setTripFrequency(tripFrequency);
        this.setWaitTime(waitTime);
        this.setVehicleTime(vehicleTime);
        this.setTravelTime(travelTime);
    }

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

    public Long getStartTimeMilli() {
        return startTimeMilli;
    }

    public String getStartTimeLocal() {
        return startTimeLocal;
    }

    public void setStartTime(Long startTime) {
        this.startTimeMilli = startTime;
        this.startTimeLocal = new Date(startTime).toLocaleString();
    }

    public Long getEndTimeMilli() {
        return endTimeMilli;
    }

    public String getEndTimeLocal() {
        return endTimeLocal;
    }

    public void setEndTime(Long endTime) {
        this.endTimeMilli = endTime;
        this.endTimeLocal = new Date(endTime).toLocaleString();
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public Double getTripLength() {
        return tripLength;
    }

    public void setTripLength(Double tripLength) {
        this.tripLength = tripLength;
    }

    public Double getTripCost() {
        return tripCost;
    }

    public void setTripCost(Double tripCost) {
        this.tripCost = tripCost;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Long getTripFrequency() {
        return tripFrequency;
    }

    public void setTripFrequency(Long tripFrequency) {
        this.tripFrequency = tripFrequency;
    }

    public Long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Long waitTime) {
        this.waitTime = waitTime;
    }

    public Long getVehicleTime() {
        return vehicleTime;
    }

    public void setVehicleTime(Long vehicleTime) {
        this.vehicleTime = vehicleTime;
    }

    public Long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Long travelTime) {
        this.travelTime = travelTime;
    }

    public ArrayList<Double> getDistanceList() {
        return distanceList;
    }

    public void setDistanceList(ArrayList<Double> distanceList) {
        this.distanceList = distanceList;
    }
}
