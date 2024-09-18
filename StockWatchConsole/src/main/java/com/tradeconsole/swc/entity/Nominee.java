package com.tradeconsole.swc.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Nominee {

    private String nomineeName;
    private String nomineeEmail;
    private String nomineeUsername;
    private String nomineeMobileNumber;

    // Getters and setters
    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getNomineeEmail() {
        return nomineeEmail;
    }

    public void setNomineeEmail(String nomineeEmail) {
        this.nomineeEmail = nomineeEmail;
    }

    public String getNomineeUsername() {
        return nomineeUsername;
    }

    public void setNomineeUsername(String nomineeUsername) {
        this.nomineeUsername = nomineeUsername;
    }

    public String getNomineeMobileNumber() {
        return nomineeMobileNumber;
    }

    public void setNomineeMobileNumber(String nomineeMobileNumber) {
        this.nomineeMobileNumber = nomineeMobileNumber;
    }
}