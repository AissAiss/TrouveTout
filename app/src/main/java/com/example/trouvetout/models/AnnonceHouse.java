package com.example.trouvetout.models;

import java.util.ArrayList;

public class AnnonceHouse extends Annonce {
    private double prixLoyer;
    private String surface;
    private double caution;

    public AnnonceHouse() {
    }

    public AnnonceHouse(String id, String nom, ArrayList<String> photo, String descpription, String position, String idOwner, String categorie, double prixLoyer, String surface, double caution) {
        super(id, nom, photo, descpription, position, idOwner, categorie);
        this.prixLoyer = prixLoyer;
        this.surface = surface;
        this.caution = caution;
    }



    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public double getPrixLoyer() {
        return prixLoyer;
    }

    public void setPrixLoyer(double prixLoyer) {
        this.prixLoyer = prixLoyer;
    }

    public double getCaution() {
        return caution;
    }

    public void setCaution(double caution) {
        this.caution = caution;
    }
}
