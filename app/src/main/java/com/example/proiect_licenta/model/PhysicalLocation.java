package com.example.proiect_licenta.model;

import java.io.Serializable;

public class PhysicalLocation implements Serializable {

    Integer id;
    Integer id_corespondent;
    String adresa;
    Double logitudine;
    Double latitudine;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_corespondent() {
        return id_corespondent;
    }

    public void setId_corespondent(Integer id_corespondent) {
        this.id_corespondent = id_corespondent;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Double getLogitudine() {
        return logitudine;
    }

    public void setLogitudine(Double logitudine) {
        this.logitudine = logitudine;
    }

    public Double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(Double latitudine) {
        this.latitudine = latitudine;
    }
}
