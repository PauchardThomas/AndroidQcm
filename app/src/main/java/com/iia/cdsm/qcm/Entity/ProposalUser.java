package com.iia.cdsm.qcm.Entity;

/**
 * Created by Thom' on 14/04/2016.
 */
public class ProposalUser {
    private long id_user;
    private int id_qcm;
    private int id_question;
    private int id_proposal;


    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public int getId_qcm() {
        return id_qcm;
    }

    public void setId_qcm(int id_qcm) {
        this.id_qcm = id_qcm;
    }

    public int getId_proposal() {
        return id_proposal;
    }

    public void setId_proposal(int id_proposal) {
        this.id_proposal = id_proposal;
    }

    public int getId_question() {
        return id_question;
    }

    public void setId_question(int id_question) {
        this.id_question = id_question;
    }

    public ProposalUser() {

    }

    public ProposalUser(long id_user,int id_qcm, int id_question, int id_proposal) {
        this.id_user = id_user;
        this.id_qcm = id_qcm;
        this.id_question = id_question;
        this.id_proposal = id_proposal;
    }
}
