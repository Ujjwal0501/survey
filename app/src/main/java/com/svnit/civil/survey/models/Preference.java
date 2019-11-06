package com.svnit.civil.survey.models;

import java.util.HashMap;
import java.util.Map;

public class Preference {

    private String change_when_first_mode_not_available;
    private String change_when_first_mode_not_available_spouse;
    private String change_when_first_mode_not_available_sd;
    private String change_when_first_mode_not_available_other;
    private String situation_switch_to_public_transport;
    private Map<String, String> attributes_public_transport;
    private Map<String, String> attributes_private_vehicle;

    public void Preference() {
        this.setChange_when_first_mode_not_available("");
        this.setChange_when_first_mode_not_available_spouse("");
        this.setChange_when_first_mode_not_available_sd("");
        this.setChange_when_first_mode_not_available_other("");
        this.setSituation_switch_to_public_transport("");
        this.setAttributes_public_transport(new HashMap<String, String>());
        this.setAttributes_private_vehicle(new HashMap<String, String>());
    }

    public void Preference(String change_when_first_mode_not_available, String situation_switch_to_public_transport,
                           String change_when_first_mode_not_available_spouse, String change_when_first_mode_not_available_sd,
                           String change_when_first_mode_not_available_other,
                           Map<String, String> attributes_private_vehicle, Map<String, String> attributes_public_transport) {

        this.setChange_when_first_mode_not_available(change_when_first_mode_not_available);
        this.setChange_when_first_mode_not_available_spouse(change_when_first_mode_not_available_spouse);
        this.setChange_when_first_mode_not_available_sd(change_when_first_mode_not_available_sd);
        this.setChange_when_first_mode_not_available_other(change_when_first_mode_not_available_other);
        this.setSituation_switch_to_public_transport(situation_switch_to_public_transport);
        this.setAttributes_public_transport(attributes_public_transport);
        this.setAttributes_private_vehicle(attributes_private_vehicle);
    }

    public String getChange_when_first_mode_not_available() {
        return change_when_first_mode_not_available;
    }

    public void setChange_when_first_mode_not_available(String change_when_first_mode_not_available) {
        this.change_when_first_mode_not_available = change_when_first_mode_not_available;
    }

    public String getSituation_switch_to_public_transport() {
        return situation_switch_to_public_transport;
    }

    public void setSituation_switch_to_public_transport(String situation_switch_to_public_transport) {
        this.situation_switch_to_public_transport = situation_switch_to_public_transport;
    }

    public Map<String, String> getAttributes_public_transport() {
        return attributes_public_transport;
    }

    public void setAttributes_public_transport(Map<String, String> attributes_public_transport) {
        this.attributes_public_transport = attributes_public_transport;
    }

    public Map<String, String> getAttributes_private_vehicle() {
        return attributes_private_vehicle;
    }

    public void setAttributes_private_vehicle(Map<String, String> attributes_private_vehicle) {
        this.attributes_private_vehicle = attributes_private_vehicle;
    }

    public String getChange_when_first_mode_not_available_spouse() {
        return change_when_first_mode_not_available_spouse;
    }

    public void setChange_when_first_mode_not_available_spouse(String change_when_first_mode_not_available_spouse) {
        this.change_when_first_mode_not_available_spouse = change_when_first_mode_not_available_spouse;
    }

    public String getChange_when_first_mode_not_available_sd() {
        return change_when_first_mode_not_available_sd;
    }

    public void setChange_when_first_mode_not_available_sd(String change_when_first_mode_not_available_sd) {
        this.change_when_first_mode_not_available_sd = change_when_first_mode_not_available_sd;
    }

    public String getChange_when_first_mode_not_available_other() {
        return change_when_first_mode_not_available_other;
    }

    public void setChange_when_first_mode_not_available_other(String change_when_first_mode_not_available_other) {
        this.change_when_first_mode_not_available_other = change_when_first_mode_not_available_other;
    }
}
