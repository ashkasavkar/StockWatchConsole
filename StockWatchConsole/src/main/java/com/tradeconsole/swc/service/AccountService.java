package com.tradeconsole.swc.service;

import com.tradeconsole.swc.dao.AccountDAO;
import com.tradeconsole.swc.entity.Account;

public class AccountService {
    private AccountDAO accountDAO = new AccountDAO();

    // Create a new account
    public void createAccount(Account account) {
        accountDAO.save(account);
    }
    public void updateAccount(Account account) {
        accountDAO.update(account);
    }

    // Update account balance
    public void updateBalance(Long accountId, double newBalance) {
        accountDAO.updateBalance(accountId, newBalance);
    }

    // Get account by user ID
    public Account getAccountByUserId(Long userId) {
        return accountDAO.findByUserId(userId);
    }
}