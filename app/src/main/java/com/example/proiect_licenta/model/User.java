package com.example.proiect_licenta.model;


public  class User  {


   private String username;
    private String email;
    private String pass;
    private String telefon;


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getTelefon() {
        return telefon;
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
