package com.example.proiect_licenta.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Service implements Serializable {

    String username;
    String email;
    String pass;
    String telefon;
    String proprietar;
    String numeService;
    String descriere;
    String program;
    String serviceId;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public ArrayList<String> getProduse() {
        return produse;
    }

    public void setProduse(ArrayList<String> produse) {
        this.produse = produse;
    }

    PhysicalLocation loc;
    ArrayList<Serviciu> sevicii;
    ArrayList<String> produse;

    public PhysicalLocation getLoc() {
        return loc;
    }

    public void setLoc(PhysicalLocation loc) {
        this.loc = loc;
    }

    public ArrayList<Serviciu> getSevicii() {
        return sevicii;
    }

    public void setSevicii(ArrayList<Serviciu> sevicii) {
        this.sevicii = sevicii;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getProprietar() {
        return proprietar;
    }

    public void setProprietar(String proprietar) {
        this.proprietar = proprietar;
    }

    public String getNumeService() {
        return numeService;
    }

    public void setNumeService(String numeService) {
        this.numeService = numeService;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}
