package com.svnit.civil.survey.models;

public class UserAddress {
    private String locality, city, state, pincode;

    public UserAddress() {
        this.city = "";
        this.locality = "";
        this.state = "";
        this.pincode = "";
    }

    public UserAddress(String line, String city, String state, String pincode) {
        this.city = city;
        this.locality = line;
        this.state = state;
        this.pincode = pincode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
