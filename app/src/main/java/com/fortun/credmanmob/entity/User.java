package com.fortun.credmanmob.entity;

public class User {

    private Long idUser;
    private String nameUser, passwordUser;

    public User() {
        idUser = null;
        nameUser = "";
        passwordUser = "";
    }

    public User(Long idUser, String nameUser, String passwordUser) {
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.passwordUser = passwordUser;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }
}
