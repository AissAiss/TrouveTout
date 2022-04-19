package com.example.trouvetout.models;

public class User {
    private String uid;
    private String pseudo;
    private String num;

    public User(String uid, String pseudo, String num) {
        this.uid = uid;
        this.pseudo = pseudo;
        this.num = num;
    }

    public User() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
