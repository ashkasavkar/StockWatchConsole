package com.tradeconsole.swc.exception;

public class StockMarketException extends RuntimeException {
    public StockMarketException(String message) {
        super(message);
    }

    public StockMarketException(String message, Throwable cause) {
        super(message, cause);
    }
}