package com.tradeconsole.swc.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.tradeconsole.swc.entity.FavStock;
import com.tradeconsole.swc.entity.StockDisplay;
import com.tradeconsole.swc.entity.User;

import java.util.List;

public class FavStockDAO extends GenericDAO<FavStock, Long> {

    public FavStockDAO() {
        super(FavStock.class);
    }

    // Find a FavStock entity by user and stock
    public FavStock findByUserAndStock(User user, StockDisplay stock) {
        try (Session session = getSession()) {
            Query<FavStock> query = session.createQuery("FROM FavStock WHERE user = :user AND stock = :stock", FavStock.class);
            query.setParameter("user", user);
            query.setParameter("stock", stock);
            return query.uniqueResult();
        }
    }
    public void addFavoriteStock(User user, StockDisplay stock) {
        FavStock favStock = findByUserAndStock(user, stock);
        if (favStock == null) {
            favStock = new FavStock();
            favStock.setUser(user);
            favStock.setStock(stock);
            favStock.setAddStock(true);
            save(favStock);
        } else {
            favStock.setAddStock(true);
            update(favStock);
        }
    }

    public void removeFavoriteStock(User user, StockDisplay stock) {
        FavStock favStock = findByUserAndStock(user, stock);
        if (favStock != null) {
            favStock.setAddStock(false);
            update(favStock);
        }
    }

    // Get all favorite stocks for a user
    public List<StockDisplay> findFavoritesByUser(User user) {
        try (Session session = getSession()) {
            Query<StockDisplay> query = session.createQuery(
                "SELECT f.stock FROM FavStock f WHERE f.user = :user AND f.addStock = true", StockDisplay.class);
            query.setParameter("user", user);
            return query.list();
        }
    }
}