package com.example.trouvetout.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Annonce {
    private String nom;
    private String photo;
    private String descpription;
    private String position;

    public Annonce(){

    }

    public Annonce(String nom, String photo, String descpription, String position) {
        this.nom = nom;
        this.photo = photo;
        this.descpription = descpription;
        this.position = position;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescpription() {
        return descpription;
    }

    public void setDescpription(String descpription) {
        this.descpription = descpription;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Annonce{" +
                "nom='" + nom + '\'' +
                ", photo='" + photo + '\'' +
                ", descpription='" + descpription + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
