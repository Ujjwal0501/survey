package com.svnit.civil.survey.models;

public class Info {
    private String age, occupation;

    public void Info() {
        this.age = "";
        this.occupation = "";
    }

    public void Info(String age, String occupation) {
        this.occupation = occupation;
        this.age = age;
    }

    public String getAge() {
        return this.age;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}