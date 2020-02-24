package com.example.proiect_licenta.model;


import java.io.Serializable;

public  class User  implements Serializable {

    private String id;
   private String username;
    private String email;
    private String password;
    private String phoneNumber;


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelefon() {
        return phoneNumber;
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.password = pass;
    }

    public void setTelefon(String telefon) {
        this.phoneNumber = telefon;
    }
}
