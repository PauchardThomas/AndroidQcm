package com.iia.cdsm.qcm.Entity;

/**
 * Created by Thom' on 11/02/2016.
 */
public class Category {

    protected int id;
    protected String libelle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Category(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }
}
