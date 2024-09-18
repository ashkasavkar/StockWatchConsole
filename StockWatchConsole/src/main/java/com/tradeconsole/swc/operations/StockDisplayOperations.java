package com.tradeconsole.swc.operations;

import com.tradeconsole.swc.entity.StockDisplay;
import com.tradeconsole.swc.service.StockDisplayService;

public class StockDisplayOperations {
    private StockDisplayService stockDisplayService;

    public StockDisplayOperations(StockDisplayService stockDisplayService) {
        this.stockDisplayService = stockDisplayService;
    }

    public void displayStock(StockDisplay stock) {
        stockDisplayService.displayStock(stock);
    }

    public StockDisplay getStockBySymbol(String symbol) {
        return stockDisplayService.getStockBySymbol(symbol);
    }
}