package com.example.proiect_licenta.model;

import android.location.Location;
import android.widget.ImageView;

public class ServiceDataModel {
    String name;
    String adresa;

    public ServiceDataModel(String name, String adresa) {
        this.name = name;
        this.adresa = adresa;

    }

    public String getName() {
        return name;
    }

    public String getAdresa() {
        return adresa;
    }


}
