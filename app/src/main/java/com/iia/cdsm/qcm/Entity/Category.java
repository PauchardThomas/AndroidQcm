package com.iia.cdsm.qcm.Entity;

import java.io.Serializable;

/**
 * Created by Thom' on 11/02/2016.
 */
public class Category implements Serializable {

    /**
     * Category ID
     */
    protected long id;
    /**
     * Category name
     */
    protected String libelle;
    /**
     * Category ID server
     */
    protected int idServer;

    /**
     * Category SERIAL
     */
    public static final String SERIAL = "CATEGORY";


    /**
     * Get the Category id
     *
     * @return Category id
     */
    public long getId() {
        return id;
    }

    /**
     * Set an ID to Category
     *
     * @param id Category id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get Category name
     *
     * @return Category name
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Set Category name
     *
     * @param libelle Category name
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Get Category ID server
     *
     * @return ID server
     */
    public int getIdServer() {
        return idServer;
    }

    /**
     * Set Category ID server
     *
     * @param idServer Category idServer
     */
    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }


    /**
     * Display Category name
     *
     * @return category name
     */
    @Override
    public String toString() {
        return this.getLibelle();
    }

    /**
     * Category constructor id , name , idServer
     *
     * @param id       Category id
     * @param libelle  Category name
     * @param idServer Category idServer
     */
    public Category(long id, String libelle, int idServer) {
        this.id = id;
        this.libelle = libelle;
        this.idServer = idServer;
    }

    /**
     * Category constructor name , idServer
     *
     * @param libelle  Category name
     * @param idServer Category idServer
     */
    public Category(String libelle, int idServer) {
        this.libelle = libelle;
        this.idServer = idServer;
    }

    /**
     * Category empty constructor
     */
    public Category() {
    }
}
