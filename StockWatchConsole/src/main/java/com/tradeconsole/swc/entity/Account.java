package com.tradeconsole.swc.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @NotNull(message = "Utilize balance cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Utilize balance must be greater than or equal to 0")
    private Double utilizeBalance;

    @NotNull(message = "Available balance cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Available balance must be greater than or equal to 0")
    private Double availableBalance;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    // Getters and setters
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Double getUtilizeBalance() {
        return utilizeBalance;
    }

    public void setUtilizeBalance(Double utilizeBalance) {
        this.utilizeBalance = utilizeBalance;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}