package com.example.proiect_licenta.model;

import java.util.ArrayList;
import java.util.Date;

public class Request {


    private ArrayList<Serviciu> servicii;
    private Date dataProgramare;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    private String detalii;
    private User client;
    private String status;
    private String produs;
    private Date dataTrimiterii;
    private Service service;
    private String requestId;


    public Date getDataTrimiterii() {
        return dataTrimiterii;
    }

    public void setDataTrimiterii(Date dataTrimiterii) {
        this.dataTrimiterii = dataTrimiterii;
    }

    public String getProdus() {
        return produs;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public ArrayList<Serviciu> getServicii() {
        return servicii;
    }

    public void setServicii(ArrayList<Serviciu> servicii) {
        this.servicii = servicii;
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
