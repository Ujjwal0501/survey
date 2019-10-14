package com.svnit.civil.survey.models;

public class Economic {
    private String monthly_income;
    private String residence_type;
    private String car;
    private String two_wheeler;
    private String bicycle;
    private String monthly_emi;
    private String monthly_fuel_cost;
    private String monthly_maintenance_cost;
    private String vehicle_registration;
    private String monthly_parking_charges;

    public void Economic() {
        this.setMonthly_income("");
        this.setResidence_type("");
        this.setCar("");
        this.setTwo_wheeler("");
        this.setBicycle("");
        this.setMonthly_emi("");
        this.setMonthly_fuel_cost("");
        this.setMonthly_maintenance_cost("");
        this.setVehicle_registration("");
        this.setMonthly_parking_charges("");
    }

    public void Economic(String monthly_income, String residence_type, String car, String two_wheeler, String bicycle, String
                         monthly_emi, String monthly_fuel_cost, String monthly_maintenance_cost, String vehicle_registration, String monthly_parking_charges) {
        this.setMonthly_income(monthly_income);
        this.setResidence_type(residence_type);
        this.setCar(car);
        this.setTwo_wheeler(two_wheeler);
        this.setBicycle(bicycle);
        this.setMonthly_emi(monthly_emi);
        this.setMonthly_fuel_cost(monthly_fuel_cost);
        this.setMonthly_maintenance_cost(monthly_maintenance_cost);
        this.setVehicle_registration(vehicle_registration);
        this.setMonthly_parking_charges(monthly_parking_charges);
    }

    public String getMonthly_income() {
        return monthly_income;
    }

    public void setMonthly_income(String monthly_income) {
        this.monthly_income = monthly_income;
    }

    public String getResidence_type() {
        return residence_type;
    }

    public void setResidence_type(String residence_type) {
        this.residence_type = residence_type;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getTwo_wheeler() {
        return two_wheeler;
    }

    public void setTwo_wheeler(String two_wheeler) {
        this.two_wheeler = two_wheeler;
    }

    public String getBicycle() {
        return bicycle;
    }

    public void setBicycle(String bicycle) {
        this.bicycle = bicycle;
    }

    public String getMonthly_emi() {
        return monthly_emi;
    }

    public void setMonthly_emi(String monthly_emi) {
        this.monthly_emi = monthly_emi;
    }

    public String getMonthly_fuel_cost() {
        return monthly_fuel_cost;
    }

    public void setMonthly_fuel_cost(String monthly_fuel_cost) {
        this.monthly_fuel_cost = monthly_fuel_cost;
    }

    public String getMonthly_maintenance_cost() {
        return monthly_maintenance_cost;
    }

    public void setMonthly_maintenance_cost(String monthly_maintenance_cost) {
        this.monthly_maintenance_cost = monthly_maintenance_cost;
    }

    public String getVehicle_registration() {
        return vehicle_registration;
    }

    public void setVehicle_registration(String vehicle_registration) {
        this.vehicle_registration = vehicle_registration;
    }

    public String getMonthly_parking_charges() {
        return monthly_parking_charges;
    }

    public void setMonthly_parking_charges(String monthly_parking_charges) {
        this.monthly_parking_charges = monthly_parking_charges;
    }
}
