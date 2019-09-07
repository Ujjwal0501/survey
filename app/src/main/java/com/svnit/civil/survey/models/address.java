package com.svnit.civil.survey.models;

public class address {
    private String city, locality, pincode, state;

    public address(String city, String locality, String pincode, String state) {
        this.city = city;
        this.locality = locality;
        this.pincode = pincode;
        this.state = state;
    }

    public address() {
        this.city = "";
        this.locality = "";
        this.state = "";
        this.pincode = "";
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
