package com.tradeconsole.swc.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.tradeconsole.swc.entity.StockDisplay;
import com.tradeconsole.swc.util.HibernateUtil;

import java.util.List;

public class StockDisplayDAO extends GenericDAO<StockDisplay, Long> {

    public StockDisplayDAO() {
        super(StockDisplay.class);
    }

    // Find stock by its symbol
    public StockDisplay findBySymbol(String symbol) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        StockDisplay stock = null;
        try {
            String hql = "FROM StockDisplay WHERE stockSymbol = :symbol";
            Query<StockDisplay> query = session.createQuery(hql, StockDisplay.class);
            query.setParameter("symbol", symbol);
            stock = query.uniqueResult();
        } finally {
            session.close();
        }
        return stock;
    }

    // Find stocks within a price range
    public List<StockDisplay> findByPriceRange(double minPrice, double maxPrice) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<StockDisplay> stocks = null;
        try {
            String hql = "FROM StockDisplay WHERE stockPrice BETWEEN :minPrice AND :maxPrice";
            Query<StockDisplay> query = session.createQuery(hql, StockDisplay.class);
            query.setParameter("minPrice", minPrice);
            query.setParameter("maxPrice", maxPrice);
            stocks = query.list();
        } finally {
            session.close();
        }
        return stocks;
    }

    // Find stocks by name
    public List<StockDisplay> findByName(String stockName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<StockDisplay> stocks = null;
        try {
            String hql = "FROM StockDisplay WHERE stockName LIKE :stockName";
            Query<StockDisplay> query = session.createQuery(hql, StockDisplay.class);
            query.setParameter("stockName", "%" + stockName + "%");
            stocks = query.list();
        } finally {
            session.close();
        }
        return stocks;
    }
}