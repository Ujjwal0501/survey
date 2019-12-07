package com.tep.sustainability.survey.models;

public class email {
    private String status, value;

    public email(String status, String value) {
        this.status = status;
        this.value = value;
    }

    public email() {
        this.status = "";
        this.value = "";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
