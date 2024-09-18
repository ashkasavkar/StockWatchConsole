package com.tradeconsole.swc.service;

import com.google.gson.reflect.TypeToken;
import com.tradeconsole.swc.dao.MarketNewsDAO;
import com.tradeconsole.swc.entity.MarketNews;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import com.google.gson.Gson;

public class MarketNewsService {
    private MarketNewsDAO marketNewsDAO = new MarketNewsDAO();
    private Gson gson = new Gson();

    // Save a single MarketNews entity to the database
    public void save(MarketNews marketNews) {
        marketNewsDAO.save(marketNews);
    }

    // Fetch market news from JSON file and save to the database
    public void fetchAndSaveMarketNewsFromFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Type marketNewsListType = new TypeToken<List<MarketNews>>() {}.getType();
            List<MarketNews> newsList = gson.fromJson(reader, marketNewsListType);

            for (MarketNews news : newsList) {
                marketNewsDAO.save(news);
            }

            System.out.println("Market news successfully loaded from file.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load market news from file.");
        }
    }

    // Find news by headline keyword
    public List<MarketNews> findNewsByHeadline(String keyword) {
        return marketNewsDAO.findByHeadline(keyword);
    }

    // Retrieve the latest news
    public List<MarketNews> getLatestNews() {
        return marketNewsDAO.findLatestNews();
    }
}