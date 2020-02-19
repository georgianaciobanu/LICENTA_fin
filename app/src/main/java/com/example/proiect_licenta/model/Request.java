package com.example.proiect_licenta.model;

import java.util.Date;

public class Request {


    private Serviciu serviciu;
    private Date dataProgramare;
    private String detalii;
    private User client;
    private String status;
    private String produs;
    private Date dataTrimiterii;


    public Date getDataTrimiterii() {
        return dataTrimiterii;
    }

    public void setDataTrimiterii(Date dataTrimiterii) {
        this.dataTrimiterii = dataTrimiterii;
    }

    public String getProdus() {
        return produs;
    }

    public void setProdus(String produs) {
        this.produs = produs;
    }

    public Date getDataProgramare() {
        return dataProgramare;
    }

    public void setDataProgramare(Date dataProgramare) {
        this.dataProgramare = dataProgramare;
    }

    public String getDetalii() {
        return detalii;
    }

    public void setDetalii(String detalii) {
        this.detalii = detalii;
    }

    public Serviciu getServiciu() {
        return serviciu;
    }

    public void setServiciu(Serviciu serviciu) {
        this.serviciu = serviciu;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
