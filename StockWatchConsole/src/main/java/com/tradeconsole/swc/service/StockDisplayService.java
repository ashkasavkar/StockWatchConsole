package com.tradeconsole.swc.service;

import java.util.List;

import com.tradeconsole.swc.dao.StockDisplayDAO;
import com.tradeconsole.swc.entity.StockDisplay;

public class StockDisplayService {
    private StockDisplayDAO stockDisplayDAO = new StockDisplayDAO();
    
    public void saveStockDisplay(StockDisplay stockDisplay) {
        try {
            stockDisplayDAO.save(stockDisplay);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception, possibly rethrow it as a custom exception
        }
    }

    // Update stock data in the database
    public void updateStockData(StockDisplay stockDisplay) {
        // Check if the stock already exists in the database
        StockDisplay existingStock = stockDisplayDAO.findBySymbol(stockDisplay.getStockSymbol());
        if (existingStock != null) {
            // Update existing stock data
            existingStock.setStockName(stockDisplay.getStockName());
            existingStock.setStockPrice(stockDisplay.getStockPrice());
            existingStock.setStockPercent(stockDisplay.getStockPercent());
            existingStock.setPreviousClosePrice(stockDisplay.getPreviousClosePrice());
            stockDisplayDAO.update(existingStock);
        } else {
            // Save new stock data
            stockDisplayDAO.save(stockDisplay);
        }
    }

    // Retrieve all stocks from the database
    public List<StockDisplay> getAllStocks() {
        return stockDisplayDAO.findAll();
    }

    // Retrieve a stock by its symbol
    public StockDisplay getStockBySymbol(String symbol) {
        return stockDisplayDAO.findBySymbol(symbol);
    }

    // Method to display stock details
    public void displayStock(StockDisplay stock) {
        System.out.println("Stock Details:");
        System.out.println("Symbol: " + stock.getStockSymbol());
        System.out.println("Name: " + stock.getStockName());
        System.out.println("Price: ₹" + stock.getStockPrice());
        System.out.println("Percent Change: " + stock.getStockPercent() + "%");
        System.out.println("Previous Close Price: ₹" + stock.getPreviousClosePrice());
        System.out.println();
    }
}