package com.iia.cdsm.qcm.Entity;

import java.util.Date;

/**
 * Created by Thom' on 11/02/2016.
 */
public class Qcm {

    protected int id;
    protected String libelle;
    protected Date datePubli;
    protected Date dateFin;
    protected int nbPoints;
    protected Category category;


    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Date getDatePubli() {
        return datePubli;
    }

    public void setDatePubli(Date datePubli) {
        this.datePubli = datePubli;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Qcm(int id, String libelle, Date datePubli, Category category,
               int nbPoints, Date dateFin) {
        this.id = id;
        this.libelle = libelle;
        this.datePubli = datePubli;
        this.category = category;
        this.nbPoints = nbPoints;
        this.dateFin = dateFin;
    }
}