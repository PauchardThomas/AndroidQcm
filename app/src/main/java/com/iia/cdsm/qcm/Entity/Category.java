package com.iia.cdsm.qcm.Entity;

import java.io.Serializable;

/**
 * Created by Thom' on 11/02/2016.
 */
public class Category implements Serializable {

    protected int id;
    protected String libelle;
    protected int idServer;
    public static final String SERIAL ="CATEGORY";

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

    public int getIdServer() {
        return idServer;
    }

    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    public String toString(){
        return this.getLibelle();
    }

    public Category(int id, String libelle,int idServer) {
        this.id = id;
        this.libelle = libelle;
        this.idServer = idServer;
    }
    public Category(String libelle,int idServer) {
        this.libelle = libelle;
        this.idServer = idServer;
    }
    public Category() {

    }
}
