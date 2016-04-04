package com.iia.cdsm.qcm.Entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Thom' on 11/02/2016.
 */
public class Qcm implements Serializable {

    protected int id;
    protected String libelle;
    protected Date datePubli;
    protected Date dateFin;
    protected Date duration;
    protected int nbPoints;
    protected Category category;
    protected int idServer;
    protected ArrayList<Question> questions;
    public static final String SERIAL = "QCM";

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

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

    public int getIdServer() {
        return idServer;
    }

    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    public String toString(){
        return this.getLibelle();
    }
    public Qcm() {

    }

    public Qcm(int id, String libelle, Date datePubli, Category category,
               int nbPoints, Date dateFin,int idServer,Date duration) {
        this.id = id;
        this.libelle = libelle;
        this.datePubli = datePubli;
        this.category = category;
        this.nbPoints = nbPoints;
        this.dateFin = dateFin;
        this.idServer = idServer;
        this.duration = duration;
    }
}
