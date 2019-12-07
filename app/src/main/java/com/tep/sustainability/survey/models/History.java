package com.tep.sustainability.survey.models;

public class History {
    private String TransactionId, DonationType, Status, Link;

    public History(String transactionId, String donationType, String status, String link) {
        TransactionId = transactionId;
        DonationType = donationType;
        Status = status;
        Link = link;
    }

    public History() {
        TransactionId = "";
        DonationType = "";
        Status = "";
        Link = "";
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public String getDonationType() {
        return DonationType;
    }

    public void setDonationType(String donationType) {
        DonationType = donationType;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}
