package com.tep.sustainability.survey.models;

import java.util.HashMap;
import java.util.Map;

public class RatedPreference {
    private Map<String, String> important_attributes;
    private Map<String, String> major_issue;
    private Map<String, String> policy_rank;

    public void RatedPreference() {
        this.setMajor_issue(new HashMap<String, String>());
        this.setImportant_attributes(new HashMap<String, String>());
        this.setPolicy_rank(new HashMap<String, String>());
    }

    public void RatedPreference(HashMap<String, String> important_attributes, HashMap<String, String> major_issue, HashMap<String, String> policy_rank) {
        this.setMajor_issue(major_issue);
        this.setImportant_attributes(important_attributes);
        this.setPolicy_rank(policy_rank);
    }

    public Map<String, String> getImportant_attributes() {
        return important_attributes;
    }

    public void setImportant_attributes(Map<String, String> important_attributes) {
        this.important_attributes= important_attributes;
    }

    public Map<String, String> getMajor_issue() {
        return major_issue;
    }

    public void setMajor_issue(Map<String, String> major_issue) {
        this.major_issue= major_issue;
    }

    public Map<String, String> getPolicy_rank() {
        return policy_rank;
    }

    public void setPolicy_rank(Map<String, String> policy_rank) {
        this.policy_rank= policy_rank;
    }
}
