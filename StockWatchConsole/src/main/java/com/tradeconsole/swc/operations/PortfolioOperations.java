package com.tradeconsole.swc.operations;

import com.tradeconsole.swc.entity.Portfolio;
import com.tradeconsole.swc.service.PortfolioService;

import java.util.List;

public class PortfolioOperations {
    private PortfolioService portfolioService;

    public PortfolioOperations(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    public void createPortfolio(Portfolio portfolio) {
        portfolioService.createPortfolio(portfolio);
    }

    public List<Portfolio> getPortfoliosByUserId(Long userId) {
        return portfolioService.getPortfoliosByUserId(userId);
    }
}