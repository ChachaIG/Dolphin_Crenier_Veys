package com.henallux.dolphin_crenier_veys.model;

public class Piscine {

    private Integer id;
    private String nom;
    private double adrLatitude;
    private double adrLongitutde;

    public Piscine(Integer id, String nom, double adrLatitude, double adrLongitutde) {
        this.id = id;
        this.nom = nom;
        this.adrLatitude = adrLatitude;
        this.adrLongitutde = adrLongitutde;
    }


    public Piscine() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getAdrLatitude() {
        return adrLatitude;
    }

    public void setAdrLatitude(double adrLatitude) {
        this.adrLatitude = adrLatitude;
    }

    public double getAdrLongitutde() {
        return adrLongitutde;
    }

    public void setAdrLongitutde(double adrLongitutde) {
        this.adrLongitutde = adrLongitutde;
    }
}
