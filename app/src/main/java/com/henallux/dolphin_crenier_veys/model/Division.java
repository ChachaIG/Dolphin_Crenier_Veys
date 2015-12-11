package com.henallux.dolphin_crenier_veys.model;


public class Division {

    private Integer idDivision;
    private String nomDivision;
    private String libelleDivision;

    public Division() {

    }

    public Division(Integer idDivision, String nomDivision, String libelleDivision) {
        this.idDivision = idDivision;
        this.nomDivision = nomDivision;
        this.libelleDivision = libelleDivision;
    }

    public Integer getIdDivision() {
        return idDivision;
    }

    public void setIdDivision(Integer idDivision) {
        this.idDivision = idDivision;
    }

    public String getNomDivision() {
        return nomDivision;
    }

    public void setNomDivision(String nomDivision) {
        this.nomDivision = nomDivision;
    }

    public String getLibelleDivision() {
        return libelleDivision;
    }

    public void setLibelleDivision(String libelleDivision) {
        this.libelleDivision = libelleDivision;
    }
}
