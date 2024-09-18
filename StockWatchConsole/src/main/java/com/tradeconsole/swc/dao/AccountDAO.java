package com.tradeconsole.swc.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.tradeconsole.swc.entity.Account;

public class AccountDAO extends GenericDAO<Account, Long> {

    public AccountDAO() {
        super(Account.class);
    }

    // Find an account by user ID
    public Account findByUserId(Long userId) {
        Session session = getSession();
        try {
            Query<Account> query = session.createQuery("FROM Account WHERE userId = :userId", Account.class);
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }

    // Update account balance
    public void updateBalance(Long accountId, double newBalance) {
        Session session = getSession();
        try {
            session.beginTransaction();
            Query<?> query = session.createQuery("UPDATE Account SET availableBalance = :newBalance WHERE accountId = :accountId");
            query.setParameter("newBalance", newBalance);
            query.setParameter("accountId", accountId);
            query.executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}