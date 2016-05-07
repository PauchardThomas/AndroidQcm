package com.iia.cdsm.qcm.Entity;

import java.io.Serializable;

/**
 * Created by Thom' on 11/02/2016.
 */
public class Proposal implements Serializable {

    /**
     * Proposal id
     */
    protected int id;
    /**
     * Proposal name
     */
    protected String libelle;
    /**
     * Proposal , if proposal is answer
     */
    protected boolean isAnswer;
    /**
     * Proposal's Question
     */
    protected Question question;
    /**
     * Proposal ID server
     */
    protected int idServer;

    /**
     * Get Proposal ID
     *
     * @return Proposal ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set Proposal ID
     *
     * @param id Proposal id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get Proposal name
     *
     * @return Proposal name
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Set Proposal name
     *
     * @param libelle Proposal name
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Get if Proposal is Answer
     *
     * @return Proposal isAnswer
     */
    public boolean isAnswer() {
        return isAnswer;
    }

    /**
     * Set Proposal isAnswer
     *
     * @param isAnswer Proposal isAnswer
     */
    public void setIsAnswer(boolean isAnswer) {
        this.isAnswer = isAnswer;
    }

    /**
     * Get Proposal's question
     *
     * @return Proposal's question
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * Set Proposal's question
     *
     * @param question Proposal's question
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

    /**
     * Get Proposal ID server
     *
     * @return Proposal ID server
     */
    public int getIdServer() {
        return idServer;
    }

    /**
     * Set Proposal ID server
     *
     * @param idServer Proposal ID server
     */
    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    /**
     * Display Proposal's name
     *
     * @return Proposal's name
     */
    @Override
    public String toString() {
        return this.getLibelle();
    }

    /**
     * Proposal constructor
     *
     * @param id       Proposal id
     * @param libelle  Proposal name
     * @param isAnswer Proposal isAnswer
     * @param question Proposal question
     * @param idServer Proposal ID server
     */
    public Proposal(int id, String libelle, boolean isAnswer, Question question, int idServer) {
        this.id = id;
        this.libelle = libelle;
        this.isAnswer = isAnswer;
        this.question = question;
        this.idServer = idServer;
    }

    /**
     * Proposal empty constructor
     */
    public Proposal() {
    }
}
