package com.example.proiect_licenta.model;

import android.location.Location;
import android.widget.ImageView;

public class ServiceDataModel {
    String name;
    String adresa;
    String program;
    PhysicalLocation locatie;

    public ServiceDataModel(String name, String adresa, String program,PhysicalLocation locatie) {
        this.name = name;
        this.adresa = adresa;
        this.program= program;
        this.locatie=locatie;


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
