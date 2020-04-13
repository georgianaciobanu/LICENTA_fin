package com.example.proiect_licenta.model;

public class Review {

    private String idService;
    private String idClient;
    private String comment;
    private String rateValue;


    public Review() {

    }

    public Review(String idService, String idClient, String comment, String rateValue) {
        this.idService = idService;
        this.idClient = idClient;
        this.comment = comment;
        this.rateValue = rateValue;
    }

    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }
}
