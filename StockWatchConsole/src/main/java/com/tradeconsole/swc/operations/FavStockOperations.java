package com.tradeconsole.swc.operations;

import com.tradeconsole.swc.entity.StockDisplay;
import com.tradeconsole.swc.entity.User;
import com.tradeconsole.swc.service.FavStockService;

import java.util.List;

public class FavStockOperations {
    private FavStockService favStockService;

    public FavStockOperations(FavStockService favStockService) {
        this.favStockService = favStockService;
    }

    public void addStockToFavorites(User user, StockDisplay stock) {
        favStockService.addStockToFavorites(user, stock);
    }

    public void removeStockFromFavorites(User user, StockDisplay stock) {
        favStockService.removeStockFromFavorites(user, stock);
    }

    public List<StockDisplay> getFavoriteStocks(User user) {
        return favStockService.getFavoriteStocks(user);
    }
}
