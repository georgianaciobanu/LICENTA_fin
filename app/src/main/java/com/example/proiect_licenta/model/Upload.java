package com.example.proiect_licenta.model;

import java.io.Serializable;

public class Upload implements Serializable {

    private String mImageUrl;
    private String emailService;


    public String getEmailService() {
        return emailService;
    }

    public void setEmailService(String emailService) {
        this.emailService = emailService;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
