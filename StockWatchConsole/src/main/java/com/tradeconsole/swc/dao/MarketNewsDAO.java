package com.tradeconsole.swc.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import com.tradeconsole.swc.entity.MarketNews;

import java.util.List;

public class MarketNewsDAO extends GenericDAO<MarketNews, Long> {

    public MarketNewsDAO() {
        super(MarketNews.class);
    }
    
    // Method to fetch all market news records
    public List<MarketNews> findAll() {
        Session session = getSession(); // Use the session from GenericDAO
        List<MarketNews> results = null;
        try {
            Query<MarketNews> query = session.createQuery("FROM MarketNews", MarketNews.class);
            results = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception appropriately
        } finally {
            session.close();
        }
        return results;
    }

    // Method to find news by headline keyword
    public List<MarketNews> findByHeadline(String keyword) {
        Session session = getSession();
        List<MarketNews> results = null;
        try {
            Query<MarketNews> query = session.createQuery("FROM MarketNews WHERE headline LIKE :keyword", MarketNews.class);
            query.setParameter("keyword", "%" + keyword + "%");
            results = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception appropriately
        } finally {
            session.close();
        }
        return results;
    }

    // Method to retrieve the latest news
    public List<MarketNews> findLatestNews() {
        Session session = getSession();
        List<MarketNews> results = null;
        try {
            Query<MarketNews> query = session.createQuery("FROM MarketNews ORDER BY publishedAt DESC", MarketNews.class);
            query.setMaxResults(10); // Retrieve the latest 10 news items
            results = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception appropriately
        } finally {
            session.close();
        }
        return results;
    }
 // Method to delete all market news records
    public void deleteAll() {
        Session session = getSession();
        try {
            session.beginTransaction();
            Query<?> query = session.createQuery("DELETE FROM MarketNews");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}