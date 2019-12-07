package com.tep.sustainability.survey.models;

public class User {
    private address address;
    private email email;
    private String name;
    private phone phone;

    public User() {
    }

    public User(com.tep.sustainability.survey.models.address address, com.tep.sustainability.survey.models.email email, String name, com.tep.sustainability.survey.models.phone phone) {
        this.address = address;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public com.tep.sustainability.survey.models.address getAddress() {
        return address;
    }

    public void setAddress(com.tep.sustainability.survey.models.address address) {
        this.address = address;
    }

    public com.tep.sustainability.survey.models.email getEmail() {
        return email;
    }

    public void setEmail(com.tep.sustainability.survey.models.email email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public com.tep.sustainability.survey.models.phone getPhone() {
        return phone;
    }

    public void setPhone(com.tep.sustainability.survey.models.phone phone) {
        this.phone = phone;
    }
}
