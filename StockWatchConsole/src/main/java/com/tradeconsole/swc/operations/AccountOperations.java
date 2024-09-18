package com.tradeconsole.swc.operations;

import com.tradeconsole.swc.entity.Account;
import com.tradeconsole.swc.entity.User;
import com.tradeconsole.swc.service.AccountService;

public class AccountOperations {
    private AccountService accountService;

    public AccountOperations(AccountService accountService) {
        this.accountService = accountService;
    }

    public void initializeAccount(User user) {
        Account account = new Account();
        account.setUtilizeBalance(0.0);
        account.setAvailableBalance(5000.0);
        account.setUser(user);
        accountService.createAccount(account);
        System.out.println("Account initialized with a balance of 5000 INR!");
    }

    public Account getAccountByUserId(Long userId) {
        return accountService.getAccountByUserId(userId);
    }

    public void updateAccount(Account account) {
        accountService.updateAccount(account);
    }
}
