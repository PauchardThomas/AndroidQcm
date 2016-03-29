package com.iia.cdsm.qcm.Entity;

import java.io.Serializable;

/**
 * Created by Thom' on 11/02/2016.
 */
public class User implements Serializable{
    protected long id;
    protected String username;
    protected String password;
    protected int idServer;
    public static final String SERIAL = "User";



    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdServer() {
        return idServer;
    }

    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    public User(int id, String username, String password , int idServer) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.idServer = idServer;
    }

    public User(String username,String password,int idServer) {
        this.username = username;
        this.password = password;
        this.idServer = idServer;
    }
}


