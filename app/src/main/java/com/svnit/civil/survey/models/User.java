package com.svnit.civil.survey.models;

public class User {
    private address address;
    private email email;
    private String name;
    private phone phone;

    public User() {
    }

    public User(com.svnit.civil.survey.models.address address, com.svnit.civil.survey.models.email email, String name, com.svnit.civil.survey.models.phone phone) {
        this.address = address;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    public com.svnit.civil.survey.models.address getAddress() {
        return address;
    }

    public void setAddress(com.svnit.civil.survey.models.address address) {
        this.address = address;
    }

    public com.svnit.civil.survey.models.email getEmail() {
        return email;
    }

    public void setEmail(com.svnit.civil.survey.models.email email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public com.svnit.civil.survey.models.phone getPhone() {
        return phone;
    }

    public void setPhone(com.svnit.civil.survey.models.phone phone) {
        this.phone = phone;
    }
}
