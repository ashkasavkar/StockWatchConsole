package com.tradeconsole.swc.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tradeconsole.swc.entity.StockDisplay;

import java.io.FileReader;
import java.io.IOException;

public class StockAPIService {

    private static final String JSON_FILE_PATH = "IndianStock.json"; // Path to your JSON file

    public StockDisplay fetchStockData(String symbol) {
        StockDisplay stockDisplay = null;

        try (FileReader fileReader = new FileReader(JSON_FILE_PATH)) {
            // Parse JSON file
            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(fileReader, JsonObject.class);
            JsonArray jsonStocks = jsonResponse.getAsJsonArray("data");

            // Iterate through the stocks to find the one with the matching symbol
            if (jsonStocks != null && jsonStocks.size() > 0) {
                for (int i = 0; i < jsonStocks.size(); i++) {
                    JsonObject stockObject = jsonStocks.get(i).getAsJsonObject();
                    if (stockObject.get("symbol").getAsString().equals(symbol)) {
                        stockDisplay = new StockDisplay();
                        stockDisplay.setStockName(stockObject.get("name").getAsString());
                        stockDisplay.setStockId(stockObject.get("id").getAsLong());
                        stockDisplay.setStockPrice(stockObject.get("price").getAsDouble());
                        stockDisplay.setStockPercent(stockObject.get("percentChange").getAsDouble());
                        stockDisplay.setPreviousClosePrice(stockObject.get("previousClosePrice").getAsDouble());
                        break; // Stop iterating once the matching stock is found
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stockDisplay;
    }
}
