package com.example.trouvetout.models;

public class Conversation {
    private String id;
    private String idAnnonces;
    private String idOwner;
    private String idClient;
    private String nomAnnonce;
    private String nomOwner;
    private String nomClient;
    private String miniature;

    public Conversation() {

    }

    public Conversation(String id, String idAnnonces, String idOwner, String idClient, String nomAnnonce, String nomOwner, String nomClient, String miniature) {
        this.id = id;
        this.idAnnonces = idAnnonces;
        this.idOwner = idOwner;
        this.idClient = idClient;
        this.nomAnnonce = nomAnnonce;
        this.nomOwner = nomOwner;
        this.nomClient = nomClient;
        this.miniature = miniature;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdAnnonces() {
        return idAnnonces;
    }

    public void setIdAnnonces(String idAnnonces) {
        this.idAnnonces = idAnnonces;
    }

    public String getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(String idOwner) {
        this.idOwner = idOwner;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getNomAnnonce() {
        return nomAnnonce;
    }

    public void setNomAnnonce(String nomAnnonce) {
        this.nomAnnonce = nomAnnonce;
    }

    public String getNomOwner() {
        return nomOwner;
    }

    public void setNomOwner(String nomOwner) {
        this.nomOwner = nomOwner;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getMiniature() {
        return miniature;
    }

    public void setMiniature(String miniature) {
        this.miniature = miniature;
    }
}
