package com.example.trouvetout.models;

import java.util.ArrayList;

public class AnnonceCar extends Annonce{
    private int kilometrage;

    public AnnonceCar() {
    }

    public AnnonceCar(String id, String nom, ArrayList<String> photo, String descpription, double longitude, double lattitude, String idOwner, String nomOwner, String categorie, int kilometrage) {
        super(id, nom, photo, descpription, longitude, lattitude, idOwner, nomOwner, categorie);
        this.kilometrage = kilometrage;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(int kilometrage) {
        this.kilometrage = kilometrage;
    }
}
