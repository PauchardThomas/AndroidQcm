package com.iia.cdsm.qcm.Entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Thom' on 11/02/2016.
 */
public class Qcm implements Serializable {

    /**
     * Qcm ID
     */
    protected int id;
    /**
     * Qcm name
     */
    protected String libelle;
    /**
     * Qcm publication date
     */
    protected Date datePubli;
    /**
     * Qcm publication end date
     */
    protected Date dateFin;
    /**
     * Qcm answer time
     */
    protected Date duration;
    /**
     * Qcm points
     */
    protected int nbPoints;
    /**
     * Qcm's Category
     */
    protected Category category;
    /**
     * Qcm ID server
     */
    protected int idServer;
    /**
     * Qcm's questions
     */
    protected ArrayList<Question> questions;
    /**
     * Qcm SERIAL
     */
    public static final String SERIAL = "QCM";

    /**
     * Get Qcm's questions
     *
     * @return list of questions
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     * Set Qcm's questions
     *
     * @param questions Qcm question
     */
    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    /**
     * Get Qcm duration
     *
     * @return duration
     */
    public Date getDuration() {
        return duration;
    }

    /**
     * Set Qcm duration
     *
     * @param duration Qcm duration
     */
    public void setDuration(Date duration) {
        this.duration = duration;
    }

    /**
     * Get Qcm name
     *
     * @return name
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Set Qcm name
     *
     * @param libelle Qcm name
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Get Qcm publication date
     *
     * @return Qcm publication date
     */
    public Date getDatePubli() {
        return datePubli;
    }

    /**
     * Set Qcm publication date
     *
     * @param datePubli Qcm publication date
     */
    public void setDatePubli(Date datePubli) {
        this.datePubli = datePubli;
    }

    /**
     * Get Qcm publication end date
     *
     * @return Qcm publication end date
     */
    public Date getDateFin() {
        return dateFin;
    }

    /**
     * set Qcm publication end date
     *
     * @param dateFin Qcm publication end date
     */
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * Get Qcm points
     *
     * @return Qcm points
     */
    public int getNbPoints() {
        return nbPoints;
    }

    /**
     * Set Qcm points
     *
     * @param nbPoints Qcm points
     */
    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    /**
     * Get Qcm id
     *
     * @return Qcm id
     */
    public int getId() {
        return id;
    }

    /**
     * Set Qcm id
     *
     * @param id Qcm id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get Qcm's Category
     *
     * @return Qcm's category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Set Qcm's Category
     *
     * @param category Qcm category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Get Qcm ID server
     *
     * @return Qcm ID server
     */
    public int getIdServer() {
        return idServer;
    }

    /**
     * Set Qcm ID server
     *
     * @param idServer Qcm ID server
     */
    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    /**
     * Display Qcm name
     *
     * @return Qcm name
     */
    @Override
    public String toString() {
        return this.getLibelle();
    }

    /**
     * Qcm constructor
     *
     * @param id        Qcm id
     * @param libelle   Qcm name
     * @param datePubli Qcm publication date
     * @param category  Qcm's category
     * @param nbPoints  Qcm points
     * @param dateFin   Qcm publication date
     * @param idServer  Qcm ID server
     * @param duration  Qcm duration
     */
    public Qcm(int id, String libelle, Date datePubli, Category category,
               int nbPoints, Date dateFin, int idServer, Date duration) {
        this.id = id;
        this.libelle = libelle;
        this.datePubli = datePubli;
        this.category = category;
        this.nbPoints = nbPoints;
        this.dateFin = dateFin;
        this.idServer = idServer;
        this.duration = duration;
    }

    /**
     * Qcm constructor
     */
    public Qcm() {
    }
}
