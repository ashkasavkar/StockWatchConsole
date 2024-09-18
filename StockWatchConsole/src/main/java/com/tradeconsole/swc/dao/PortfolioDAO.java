package com.tradeconsole.swc.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.tradeconsole.swc.entity.Portfolio;
import com.tradeconsole.swc.util.HibernateUtil;

import java.util.List;

public class PortfolioDAO extends GenericDAO<Portfolio, Long> {

    public PortfolioDAO() {
        super(Portfolio.class);
    }

    // Get portfolios by user ID
    public List<Portfolio> findByUserId(Long userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Portfolio> portfolios = null;
        try {
            String hql = "FROM Portfolio WHERE user.id = :userId";
            Query<Portfolio> query = session.createQuery(hql, Portfolio.class);
            query.setParameter("userId", userId);
            portfolios = query.list();
        } finally {
            session.close();
        }
        return portfolios;
    }

    // Calculate total investment for a user
    public double calculateTotalInvestment(Long userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Double totalInvestment = 0.0;
        try {
            String hql = "SELECT SUM(p.investment) FROM Portfolio p WHERE p.user.id = :userId";
            Query<Double> query = session.createQuery(hql, Double.class);
            query.setParameter("userId", userId);
            totalInvestment = query.uniqueResult();
        } finally {
            session.close();
        }
        return totalInvestment != null ? totalInvestment : 0.0;
    }
}