package com.example.proiect_licenta.model;

import java.util.Calendar;
import java.util.Date;

public class ChatMessage {

    private String messageText;
    private String messageUser;
    private String messageFor;
    private Date messageTime;

    public ChatMessage(String messageText, String messageUser, String messageFor) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageFor=messageFor;

        // Initialize to current time
        messageTime = Calendar.getInstance().getTime();
    }

    public ChatMessage(){

    }

    public String getMessageFor() {
        return messageFor;
    }

    public void setMessageFor(String messageFor) {
        this.messageFor = messageFor;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }
}
