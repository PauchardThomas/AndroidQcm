package com.iia.cdsm.qcm.Entity;

/**
 * Created by Thom' on 11/02/2016.
 */
public class Proposal {

    protected int id;
    protected String libelle;
    protected boolean isAnswer;
    protected Question question;

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

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(boolean isAnswer) {
        this.isAnswer = isAnswer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Proposal(int id, String libelle, boolean isAnswer, Question question) {
        this.id = id;
        this.libelle = libelle;
        this.isAnswer = isAnswer;
        this.question = question;
    }
}