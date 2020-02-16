package com.example.proiect_licenta.model;

import java.util.Date;

public class Request {

    private   String produs;
    private Date dataProgramare;
    private String detalii;


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
}
