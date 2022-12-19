package com.example.pert10_sharedpreferences;

public class Model {
    private int id;
    private byte[]proavatar;
    private String userjudul_lagu;
    private String userartis;
    private String usertahun;
    //constructor
    public Model(int id, byte[] proavatar, String userjudul_lagu, String userartis, String usertahun) {
        this.id = id;
        this.proavatar = proavatar;
        this.userjudul_lagu = userjudul_lagu;
        this.userartis = userartis;
        this.usertahun = usertahun;
    }
    //getter and setter method
    public int getId() {

        return id;
    }
    public void setId(int id) {

        this.id = id;
    }
    public byte[] getProavatar() {

        return proavatar;
    }
    public void setProavatar(byte[] proavatar) {

        this.proavatar = proavatar;
    }
    public String getUserjudul_lagu() {

        return userjudul_lagu;
    }
    public void setUserjudul_lagu(String userjudul_lagu) {

        this.userjudul_lagu = userjudul_lagu;
    }
    public String getUserartis() {
        return userartis;
    }
    public void setUserartis(String userartis) {

        this.userartis = userartis;
    }
    public String getUsertahun() {

        return usertahun;
    }
    public void setUsertahun(String usertahun) {

        this.usertahun = usertahun;
    }
}

