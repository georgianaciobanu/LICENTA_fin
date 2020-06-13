package com.example.proiect_licenta.model;

import android.location.Location;
import android.widget.ImageView;

public class ServiceDataModel {
    String name;
    String adresa;
    String program;
    PhysicalLocation locatie;
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ServiceDataModel(String name, String adresa, String program, PhysicalLocation locatie, String email) {
        this.name = name;
        this.adresa = adresa;
        this.program= program;
        this.locatie=locatie;
        this.email=email;

    }

    public String getName() {
        return name;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getProgram() {
        return program;
    }

    public PhysicalLocation getLocatie() {
        return locatie;
    }
}
