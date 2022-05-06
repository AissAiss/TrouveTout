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
    private String categorie;

    private double longitude;
    private double lattitude;

    private String idOwner;
    private String nomOwner;

    public Annonce(){

    }

    public Annonce(String id, String nom, ArrayList<String> photo, String descpription, double longitude, double lattitude, String idOwner, String nomOwner, String categorie) {
        this.id             = id;
        this.nom            = nom;
        this.photo          = photo;
        this.descpription   = descpription;
        this.longitude      = longitude;
        this.lattitude      = lattitude;
        this.idOwner        = idOwner;
        this.categorie      = categorie;
        this.nomOwner       = nomOwner;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(String idOwner) {
        this.idOwner = idOwner;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getNomOwner() {
        return nomOwner;
    }

    public void setNomOwner(String nomOwner) {
        this.nomOwner = nomOwner;
    }


    @Override
    public String toString() {
        return "Annonce{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", photo=" + photo +
                ", descpription='" + descpription + '\'' +
                ", position='" + lattitude + " : "+ longitude + '\'' +
                ", categorie='" + categorie + '\'' +
                ", idOwner='" + idOwner + '\'' +
                ", nomOwner='" + nomOwner + '\'' +
                '}';
    }
}
