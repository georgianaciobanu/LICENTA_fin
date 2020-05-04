package com.example.proiect_licenta.model;

import java.util.Date;

public class Review {

    private String idService;
    private String idClient;
    private String comment;
    private String rateValue;
    private Date data;


    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Review() {

    }

    public Review(String idService, String idClient, String comment, String rateValue, Date data) {
        this.idService = idService;
        this.idClient = idClient;
        this.comment = comment;
        this.rateValue = rateValue;
        this.data=data;
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
