package com.tradeconsole.swc.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String password;

    @Embedded
    private Nominee nominee;

    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //private Set<Portfolio> portfolios;

    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //private Set<Account> accounts;

 
   // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
   // private Set<FavStock> favStocks;

    
    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
   // private Set<History> histories;

    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //private Set<MarketNews> marketNews;

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Nominee getNominee() {
        return nominee;
    }

    public void setNominee(Nominee nominee) {
        this.nominee = nominee;
    }

   /// public Set<Portfolio> getPortfolios() {
     ///   return portfolios;}

   /// public void setPortfolios(Set<Portfolio> portfolios) {
    ///    this.portfolios = portfolios; }

    ///public Set<Account> getAccounts() {
    ///    return accounts;}

///public void setAccounts(Set<Account> accounts) {
///    this.accounts = accounts;}
}