package com.henallux.dolphin_crenier_veys.model;


public class Utilisateur {
    private Integer idUtilisateur;
    private String login;
    private String motDepasse;
    private String nom;
    private  String prenom;
    private double adrLatitude;
    private double adrLongitude;

    public Utilisateur(Integer id, String login, String motDepasse, String nom, String prenom, double adrLatitude,double adrLongitude) {
        idUtilisateur = id;
        this.login = login;
        this.motDepasse = motDepasse;
        this.nom = nom;
        this.prenom = prenom;
        this.adrLatitude = adrLatitude;
        this.adrLongitude = adrLongitude;
    }

    public Utilisateur(Integer idUtilisateur, String prenom, double adrLatitude, double adrLongitude) {
        this.idUtilisateur = idUtilisateur;
        this.prenom = prenom;
        this.adrLatitude = adrLatitude;
        this.adrLongitude = adrLongitude;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDepasse() {
        return motDepasse;
    }

    public void setMotDepasse(String motDepasse) {
        this.motDepasse = motDepasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public double getAdrLatitude() {
        return adrLatitude;
    }

    public void setAdrLatitude(double adrLatitude) {
        this.adrLatitude = adrLatitude;
    }

    public double getAdrLongitude() {
        return adrLongitude;
    }

    public void setAdrLongitude(double adrLongitude) {
        this.adrLongitude = adrLongitude;
    }
}
