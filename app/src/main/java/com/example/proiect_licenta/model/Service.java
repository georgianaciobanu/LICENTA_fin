package com.example.proiect_licenta.model;

import java.io.Serializable;

public class Service implements Serializable {

    String username;
    String email;
    String pass;
    String telefon;
    String proprietar;
    String numeService;
    String descriere;
    String program;

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
