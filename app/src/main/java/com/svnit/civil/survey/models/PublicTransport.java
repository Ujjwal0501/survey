package com.svnit.civil.survey.models;

public class PublicTransport {

    private TransportDetails cityBus;
    private TransportDetails brt;
    private TransportDetails auto;

    public void PublicTransport() {
        this.setCityBus(new TransportDetails());
        this.setBrt(new TransportDetails());
        this.setAuto(new TransportDetails());
    }

    public void PublicTransport(TransportDetails cityBus, TransportDetails brt, TransportDetails auto) {
        this.setCityBus(cityBus);
        this.setBrt(brt);
        this.setAuto(auto);
    }

    public TransportDetails getCityBus() {
        return cityBus;
    }

    public void setCityBus(TransportDetails cityBus) {
        this.cityBus = cityBus;
    }

    public TransportDetails getBrt() {
        return brt;
    }

    public void setBrt(TransportDetails brt) {
        this.brt = brt;
    }

    public TransportDetails getAuto() {
        return auto;
    }

    public void setAuto(TransportDetails auto) {
        this.auto = auto;
    }
}
