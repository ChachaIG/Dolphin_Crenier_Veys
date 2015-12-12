package com.henallux.dolphin_crenier_veys.model;


import java.util.Calendar;

public class Match {

    private Integer idMatch;
    private Calendar dateMatch;
    private Boolean secondMatch;
    private Utilisateur util;
    private Integer idUtilisateur;
    private Piscine piscine;
    private Integer idPiscine;
    private Division division;
    private Integer idDivision;
    private double distance;
    private double cout;

    public Match(Integer idMatch, Calendar dateMatch, Boolean secondMatch, Utilisateur util, Piscine piscine, Division division, double distance, double cout) {
        this.idMatch = idMatch;
        this.dateMatch = dateMatch;
        this.secondMatch = secondMatch;
        this.util = util;
        this.piscine = piscine;
        this.division = division;
        this.distance = distance;
        this.cout = cout;
    }

    public Match(Integer idMatch, Calendar dateMatch, Boolean secondMatch, Integer idUtilisateur, Integer idPiscine, Integer idDivision, double distance, double cout) {
        this.idMatch = idMatch;
        this.dateMatch = dateMatch;
        this.secondMatch = secondMatch;
        this.idUtilisateur = idUtilisateur;
        this.idPiscine = idPiscine;
        this.idDivision = idDivision;
        this.distance = distance;
        this.cout = cout;
    }

    public Match(Calendar dateMatch, Boolean secondMatch, Integer idUtilisateur, Integer idPiscine, Integer idDivision, double distance, double cout) {
        this.dateMatch = dateMatch;
        this.secondMatch = secondMatch;
        this.idUtilisateur = idUtilisateur;
        this.idPiscine = idPiscine;
        this.idDivision = idDivision;
        this.distance = distance;
        this.cout = cout;
    }

    public Integer getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Integer idMatch) {
        this.idMatch = idMatch;
    }

    public Calendar getDateMatch() {
        return dateMatch;
    }

    public void setDateMatch(Calendar dateMatch) {
        this.dateMatch = dateMatch;
    }

    public Boolean getSecondMatch() {
        return secondMatch;
    }

    public void setSecondMatch(Boolean secondMatch) {
        this.secondMatch = secondMatch;
    }

    public Utilisateur getUtil() {
        return util;
    }

    public void setUtil(Utilisateur util) {
        this.util = util;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Piscine getPiscine() {
        return piscine;
    }

    public void setPiscine(Piscine piscine) {
        this.piscine = piscine;
    }

    public Integer getIdPiscine() {
        return idPiscine;
    }

    public void setIdPiscine(Integer idPiscine) {
        this.idPiscine = idPiscine;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public Integer getIdDivision() {
        return idDivision;
    }

    public void setIdDivision(Integer idDivision) {
        this.idDivision = idDivision;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getCout() {
        return cout;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }
}
