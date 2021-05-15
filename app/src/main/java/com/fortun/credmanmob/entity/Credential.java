package com.fortun.credmanmob.entity;

public class Credential {

    private Long idCredential;
    private String nameCredential, userCredential, passwordCredential, idUserFK;

    public Credential() {
        idCredential = null;
        nameCredential = "";
        userCredential = "";
        passwordCredential = "";
        idUserFK = "";
    }

    public Credential(Long idCredential, String nameCredential, String userCredential, String passwordCredential, String idUserFK) {
        this.idCredential = idCredential;
        this.nameCredential = nameCredential;
        this.userCredential = userCredential;
        this.passwordCredential = passwordCredential;
        this.idUserFK = idUserFK;
    }

    public Long getIdCredential() {
        return idCredential;
    }

    public void setIdCredential(Long idCredential) {
        this.idCredential = idCredential;
    }

    public String getNameCredential() {
        return nameCredential;
    }

    public void setNameCredential(String nameCredential) {
        this.nameCredential = nameCredential;
    }

    public String getUserCredential() {
        return userCredential;
    }

    public void setUserCredential(String userCredential) {
        this.userCredential = userCredential;
    }

    public String getPasswordCredential() {
        return passwordCredential;
    }

    public void setPasswordCredential(String passwordCredential) {
        this.passwordCredential = passwordCredential;
    }

    public String getIdUserFK() {
        return idUserFK;
    }

    public void setIdUserFK(String idUserFK) {
        this.idUserFK = idUserFK;
    }
}
