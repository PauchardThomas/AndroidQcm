package com.iia.cdsm.qcm.Entity;

/**
 * Created by Thom' on 11/02/2016.
 */
public class Question {

    protected int id;
    protected String libelle;
    protected int points;
    protected Qcm qcm;
    protected int idServer;

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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Qcm getQcm() {
        return qcm;
    }

    public void setQcm(Qcm qcm) {
        this.qcm = qcm;
    }

    public int getIdServer() {
        return idServer;
    }

    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    public Question(int id, String libelle, int points, Qcm qcm,int idServer) {
        this.id = id;
        this.libelle = libelle;
        this.points = points;
        this.qcm = qcm;
        this.idServer = idServer;
    }
}
