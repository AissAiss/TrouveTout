package com.example.trouvetout.models;

public class Favori {
    String id;
    String idUser;
    String idAnnonce;

    public Favori() {
    }

    public Favori(String idUser, String idAnnonce) {
        this.idUser = idUser;
        this.idAnnonce = idAnnonce;
        this.id = idUser+";"+idAnnonce;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdAnnonce() {
        return idAnnonce;
    }

    public void setIdAnnonce(String idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
