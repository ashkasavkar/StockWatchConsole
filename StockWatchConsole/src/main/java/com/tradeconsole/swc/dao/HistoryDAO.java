package com.tradeconsole.swc.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.tradeconsole.swc.entity.History;

import java.util.List;

public class HistoryDAO extends GenericDAO<History, Long> {

    public HistoryDAO() {
        super(History.class);
    }

    // List history records for a specific user
    public List<History> findByUserId(Long userId) {
        Session session = getSession();
        try {
            Query<History> query = session.createQuery("FROM History WHERE userId = :userId", History.class);
            query.setParameter("userId", userId);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Get profit or loss for a specific history record
    public double getProfitOrLoss(Long historyId) {
        Session session = getSession();
        try {
            Query<Double> query = session.createQuery("SELECT profit FROM History WHERE historyId = :historyId", Double.class);
            query.setParameter("historyId", historyId);
            return query.uniqueResult() != null ? query.uniqueResult() : 0.0;
        } finally {
            session.close();
        }
    }
}