package com.svnit.civil.survey.models;

import java.util.HashMap;
import java.util.Map;

public class RatedPreference {
    private Map<String, String> important_attribute_for_choosing_transport;
    private Map<String, String> major_issue_of_transport_system;
    private Map<String, String> appropriate_policy_rank_for_better_transport;

    public void RatedPreference() {
        this.setMajor_issue_of_transport_system(new HashMap<String, String>());
        this.setImportant_attribute_for_choosing_transport(new HashMap<String, String>());
        this.setAppropriate_policy_rank_for_better_transport(new HashMap<String, String>());
    }

    public void RatedPreference(HashMap<String, String> important_attribute_for_choosing_transport, HashMap<String, String> major_issue_of_transport_system, HashMap<String, String> appropriate_policy_rank_for_better_transport) {
        this.setMajor_issue_of_transport_system(major_issue_of_transport_system);
        this.setImportant_attribute_for_choosing_transport(important_attribute_for_choosing_transport);
        this.setAppropriate_policy_rank_for_better_transport(appropriate_policy_rank_for_better_transport);
    }

    public Map<String, String> getImportant_attribute_for_choosing_transport() {
        return important_attribute_for_choosing_transport;
    }

    public void setImportant_attribute_for_choosing_transport(Map<String, String> important_attribute_for_choosing_transport) {
        this.important_attribute_for_choosing_transport = important_attribute_for_choosing_transport;
    }

    public Map<String, String> getMajor_issue_of_transport_system() {
        return major_issue_of_transport_system;
    }

    public void setMajor_issue_of_transport_system(Map<String, String> major_issue_of_transport_system) {
        this.major_issue_of_transport_system = major_issue_of_transport_system;
    }

    public Map<String, String> getAppropriate_policy_rank_for_better_transport() {
        return appropriate_policy_rank_for_better_transport;
    }

    public void setAppropriate_policy_rank_for_better_transport(Map<String, String> appropriate_policy_rank_for_better_transport) {
        this.appropriate_policy_rank_for_better_transport = appropriate_policy_rank_for_better_transport;
    }
}
