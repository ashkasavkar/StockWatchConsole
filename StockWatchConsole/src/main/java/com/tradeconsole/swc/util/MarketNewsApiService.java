package com.tradeconsole.swc.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tradeconsole.swc.dao.MarketNewsDAO;
import com.tradeconsole.swc.entity.MarketNews;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MarketNewsApiService {
    private static final String API_KEY = "your_api_key_here"; // Replace with your actual API key
    private static final String BASE_URL = "https://www.alphavantage.co/query";
    private MarketNewsDAO marketNewsDAO = new MarketNewsDAO();
    private Gson gson = new Gson();

    // Fetch market news from the API and save to the database
    public void fetchAndSaveMarketNews() {
        String urlString = String.format("%s?function=NEWS_SENTIMENT&tickers=%s&apikey=%s", BASE_URL, API_KEY);

        try {
            String jsonResponse = getJsonResponse(urlString);
            List<MarketNews> marketNewsList = parseJsonResponse(jsonResponse);

            for (MarketNews news : marketNewsList) {
                marketNewsDAO.save(news);
            }

            System.out.println("Market news successfully fetched and saved.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to fetch market news from API.");
        }
    }

    // Method to delete all market news from the database
    public void deleteAllMarketNews() {
        marketNewsDAO.deleteAll(); // Ensure this method is implemented in MarketNewsDAO
    }
    // Helper method to make an HTTP GET request and return the response as a string
    private String getJsonResponse(String urlString) throws IOException {
        URL url = createURL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.name())) {
            return scanner.useDelimiter("\\A").next();
        }
    }

    // Helper method to create a URL object from a string
    private URL createURL(String urlString) throws IOException {
        try {
            URI uri = new URI(urlString);
            return uri.toURL();
        } catch (URISyntaxException e) {
            throw new IOException("Invalid URL syntax: " + urlString, e);
        }
    }

    // Parse the JSON response from the API
    private List<MarketNews> parseJsonResponse(String jsonResponse) {
        List<MarketNews> marketNewsList = new ArrayList<>();
        JsonObject responseObject = gson.fromJson(jsonResponse, JsonObject.class);
        JsonArray newsArray = responseObject.getAsJsonArray("news");

        for (int i = 0; i < newsArray.size(); i++) {
            JsonObject newsObject = newsArray.get(i).getAsJsonObject();
            MarketNews marketNews = new MarketNews();
            marketNews.setHeadline(newsObject.get("headline").getAsString());
            marketNews.setContent(newsObject.get("content").getAsString());
            marketNews.setPublishedAt(gson.fromJson(newsObject.get("datetime"), java.util.Date.class));
            marketNews.setUrl(newsObject.get("url").getAsString());

            marketNewsList.add(marketNews);
        }

        return marketNewsList;
    }
}