package com.tradeconsole.swc.entity;

import javax.persistence.*;

@Entity
@Table(name = "stock_display")
public class StockDisplay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @Column(name = "stock_symbol", nullable = false, unique = true)
    private String stockSymbol;

    @Column(name = "stock_name", nullable = false)
    private String stockName;

    @Column(name = "stock_price", nullable = false)
    private double stockPrice;

    @Column(name = "stock_percent")
    private double stockPercent;

    @Column(name = "previous_close_price")
    private double previousClosePrice;
     
    private double buy;
    private double sell;
    
    

    // Getters and setters
    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }
    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(Double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public double getStockPercent() {
        return stockPercent;
    }

    public void setStockPercent(double stockPercent) {
        this.stockPercent = stockPercent;
    }

    public Double getPreviousClosePrice() {
        return previousClosePrice;
    }

    public void setPreviousClosePrice(Double previousClosePrice) {
        this.previousClosePrice = previousClosePrice;
    }

    

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }
}