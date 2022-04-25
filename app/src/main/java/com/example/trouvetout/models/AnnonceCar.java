package com.example.trouvetout.models;

import java.util.ArrayList;

public class AnnonceCar extends Annonce{
    private int kilometrage;

    public AnnonceCar() {
    }

    public AnnonceCar(String id, String nom, ArrayList<String> photo, String descpription, String position, String idOwner, String categorie, int kilometrage) {
        super(id, nom, photo, descpription, position, idOwner, categorie);
        this.kilometrage = kilometrage;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(int kilometrage) {
        this.kilometrage = kilometrage;
    }
}
