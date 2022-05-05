package com.example.trouvetout.models;

import java.util.Date;

public class ChatMessage {
    private String messageText;
    private String messageUser;
    private String idConversation;
    private long messageTime;

    public ChatMessage(String messageText, String messageUser, String idConversation) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.idConversation = idConversation;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(){

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

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(String idConversation) {
        this.idConversation = idConversation;
    }
}
