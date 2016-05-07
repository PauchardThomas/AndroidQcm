package com.iia.cdsm.qcm.Entity;

import java.io.Serializable;

/**
 * Created by Thom' on 11/02/2016.
 */
public class User implements Serializable {
    /**
     * User id
     */
    protected long id;
    /**
     * User username
     */
    protected String username;
    /**
     * User password
     */
    protected String password;
    /**
     * User idServer
     */
    protected int idServer;
    /**
     * User SERIAL
     */
    public static final String SERIAL = "User";

    /**
     * Get User id
     *
     * @return User id
     */
    public long getId() {
        return id;
    }

    /**
     * Set User id
     *
     * @param id User id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get User password
     *
     * @return User password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set User password
     *
     * @param password User password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get User username
     *
     * @return User username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set User username
     *
     * @param username User username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get User ID server
     *
     * @return User ID server
     */
    public int getIdServer() {
        return idServer;
    }

    /**
     * Set User ID server
     *
     * @param idServer User ID server
     */
    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    /**
     * User constructor
     *
     * @param id       User id
     * @param username User username
     * @param password User password
     * @param idServer User ID server
     */
    public User(long id, String username, String password, int idServer) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.idServer = idServer;
    }

    /**
     * User constructor
     *
     * @param username User username
     * @param password User password
     * @param idServer User ID server
     */
    public User(String username, String password, int idServer) {
        this.username = username;
        this.password = password;
        this.idServer = idServer;
    }
}


