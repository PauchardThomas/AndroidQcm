package com.iia.cdsm.qcm.Entity;

/**
 * Created by Thom' on 14/04/2016.
 */
public class ProposalUser {
    /**
     * User id
     */
    private long id_user;
    /**
     * Qcm id
     */
    private int id_qcm;
    /**
     * Question id
     */
    private int id_question;
    /**
     * Proposal id
     */
    private int id_proposal;


    /**
     * Get User ID
     *
     * @return User ID
     */
    public long getId_user() {
        return id_user;
    }

    /**
     * Set User ID
     *
     * @param id_user ProposalUser id_user
     */
    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    /**
     * Get Qcm ID
     *
     * @return Qcm ID
     */
    public int getId_qcm() {
        return id_qcm;
    }

    /**
     * Set Qcm ID
     *
     * @param id_qcm ProposalUser id_qcm
     */
    public void setId_qcm(int id_qcm) {
        this.id_qcm = id_qcm;
    }

    /**
     * Get Proposal ID
     *
     * @return Proposal id
     */
    public int getId_proposal() {
        return id_proposal;
    }

    /**
     * Set Proposal ID
     *
     * @param id_proposal Proposal id
     */
    public void setId_proposal(int id_proposal) {
        this.id_proposal = id_proposal;
    }

    /**
     * Get Question ID
     *
     * @return Question id
     */
    public int getId_question() {
        return id_question;
    }

    /**
     * Set Question ID
     *
     * @param id_question Question id
     */
    public void setId_question(int id_question) {
        this.id_question = id_question;
    }

    /**
     * ProposalUser constructor
     */
    public ProposalUser() {
    }
}
