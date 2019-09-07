package com.svnit.civil.survey.models;

public class phone {
    private String status, value;

    public phone(String status, String value) {
        this.status = status;
        this.value = value;
    }

    public phone() {
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
