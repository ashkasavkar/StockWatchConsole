package com.tradeconsole.swc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeconsole.swc.entity.StockDisplay;

public class IndianStockLoader {
	private List<StockDisplay> stocks;

    public void loadStockData() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Load JSON file from resources
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("IndianStock.json");

            if (inputStream == null) {
                throw new IllegalArgumentException("File not found! Please place the JSON file in the resources folder.");
            }

            // Read JSON data
            stocks = mapper.readValue(inputStream, new TypeReference<List<StockDisplay>>(){});
        } catch (IOException e) {
            System.err.println("Error loading stock data: " + e.getMessage());
            System.exit(1);
        }
    }

    public List<StockDisplay> getStocks() {
        return stocks;
    }
}
