package com.example.trouvetout.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Annonce {
    private String id;
    private String nom;
    private ArrayList<String> photo;
    private String descpription;
    private String position;

    public Annonce(){

    }

    public Annonce(String id, String nom, ArrayList<String> photo, String descpription, String position) {
        this.id = id;
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

    public ArrayList<String> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<String> photo) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
