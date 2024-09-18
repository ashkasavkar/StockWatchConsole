package com.tradeconsole.swc.service;

import java.util.List;

import com.tradeconsole.swc.dao.FavStockDAO;
import com.tradeconsole.swc.entity.FavStock;
import com.tradeconsole.swc.entity.StockDisplay;
import com.tradeconsole.swc.entity.User;

public class FavStockService {
    private FavStockDAO favStockDAO;

    public FavStockService() {
        favStockDAO = new FavStockDAO();
    }

    // Add a stock to favorites
    public void addStockToFavorites(User user, StockDisplay stock) {
        if (user == null || stock == null) {
            throw new IllegalArgumentException("User and stock cannot be null");
        }

        FavStock favStock = favStockDAO.findByUserAndStock(user, stock);
        if (favStock == null) {
            favStock = new FavStock();
            favStock.setUser(user);
            favStock.setStock(stock);
            favStock.setAddStock(true);
            favStock.setRemoveStock(false);
            favStockDAO.save(favStock);
        } else {
            favStock.setAddStock(true);
            favStock.setRemoveStock(false);
            favStockDAO.update(favStock);
        }
    }

    // Remove a stock from favorites
    public void removeStockFromFavorites(User user, StockDisplay stock) {
        if (user == null || stock == null) {
            throw new IllegalArgumentException("User and stock cannot be null");
        }

        FavStock favStock = favStockDAO.findByUserAndStock(user, stock);
        if (favStock != null) {
            favStock.setAddStock(false);
            favStock.setRemoveStock(true);
            favStockDAO.update(favStock);
        } else {
            throw new IllegalStateException("Stock is not in favorites");
        }
    }

    // Get all favorite stocks for a user
    public List<StockDisplay> getFavoriteStocks(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        return favStockDAO.findFavoritesByUser(user);
    }

    // Check if a stock is already a favorite
    public boolean isStockFavorite(User user, StockDisplay stock) {
        if (user == null || stock == null) {
            throw new IllegalArgumentException("User and stock cannot be null");
        }

        FavStock favStock = favStockDAO.findByUserAndStock(user, stock);
        return favStock != null && favStock.getAddStock();
    }
}