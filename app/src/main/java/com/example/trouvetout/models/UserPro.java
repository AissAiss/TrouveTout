package com.example.trouvetout.models;

public class UserPro extends User{
    private String numCard;
    private String expiration;
    private String codeSecret;

    public UserPro(String uid, String pseudo, String num, String numCard, String expiration, String codeSecret) {
        super(uid, pseudo, num);
        this.numCard = numCard;
        this.expiration = expiration;
        this.codeSecret = codeSecret;
    }

    public UserPro() {
    }

    public String getNumCard() {
        return numCard;
    }

    public void setNumCard(String numCard) {
        this.numCard = numCard;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getCodeSecret() {
        return codeSecret;
    }

    public void setCodeSecret(String codeSecret) {
        this.codeSecret = codeSecret;
    }

    @Override
    public String toString() {
        return "UserPro{" +
                "numCard='" + numCard + '\'' +
                ", expiration='" + expiration + '\'' +
                ", codeSecret='" + codeSecret + '\'' +
                '}';
    }
}
