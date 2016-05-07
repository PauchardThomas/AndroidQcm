package com.iia.cdsm.qcm.Entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Thom' on 11/02/2016.
 */
public class Question implements Serializable {

    /**
     * Question id
     */
    protected int id;
    /**
     * Question name
     */
    protected String libelle;
    /**
     * Question points
     */
    protected int points;
    /**
     * Question's qcm
     */
    protected Qcm qcm;
    /**
     * Question ID SERVER
     */
    protected int idServer;
    /**
     * Question's proposals
     */
    protected ArrayList<Proposal> proposals;
    /**
     * Question SERIAL
     */
    public static final String SERIAL = "QUESTION";

    /**
     * Get Question proposals
     *
     * @return Question proposals
     */
    public ArrayList<Proposal> getProposals() {
        return proposals;
    }

    /**
     * Set Question proposals
     *
     * @param proposals Question proposals
     */
    public void setProposals(ArrayList<Proposal> proposals) {
        this.proposals = proposals;
    }

    /**
     * Get Question id
     *
     * @return Question id
     */
    public int getId() {
        return id;
    }

    /**
     * Set Question id
     *
     * @param id Question id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get Question name
     *
     * @return Question name
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Set Question name
     *
     * @param libelle Question name
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Get Question points
     *
     * @return Question points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Set Question points
     *
     * @param points Question points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Get Question's Qcm
     *
     * @return Question's Qcm
     */
    public Qcm getQcm() {
        return qcm;
    }

    /**
     * Set Question's Qcm
     *
     * @param qcm Question's Qcm
     */
    public void setQcm(Qcm qcm) {
        this.qcm = qcm;
    }

    /**
     * Get Question ID server
     *
     * @return Question ID server
     */
    public int getIdServer() {
        return idServer;
    }

    /**
     * Set Question ID server
     *
     * @param idServer Question ID server
     */
    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    /**
     * Display Question name
     *
     * @return Question name
     */
    @Override
    public String toString() {
        return this.getLibelle();
    }

    /**
     * Question constructor
     *
     * @param id       Question id
     * @param libelle  Question name
     * @param points   Question points
     * @param qcm      Question qmc
     * @param idServer Question ID server
     */
    public Question(int id, String libelle, int points, Qcm qcm, int idServer) {
        this.id = id;
        this.libelle = libelle;
        this.points = points;
        this.qcm = qcm;
        this.idServer = idServer;
    }

    /**
     * Question constructor
     */
    public Question() {
    }
}
