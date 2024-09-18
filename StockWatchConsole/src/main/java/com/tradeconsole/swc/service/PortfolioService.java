package com.tradeconsole.swc.service;

import java.util.List;

import com.tradeconsole.swc.dao.AccountDAO;
import com.tradeconsole.swc.dao.HistoryDAO;
import com.tradeconsole.swc.dao.PortfolioDAO;
import com.tradeconsole.swc.entity.Account;
import com.tradeconsole.swc.entity.History;
import com.tradeconsole.swc.entity.Portfolio;

public class PortfolioService {
    private PortfolioDAO portfolioDAO = new PortfolioDAO();
    private AccountDAO accountDAO = new AccountDAO(); // Initialize this DAO
    private HistoryDAO historyDAO = new HistoryDAO(); // Initialize this DAO

    // Create a new portfolio entry
    public void createPortfolio(Portfolio portfolio) {
        if (portfolio == null || portfolio.getUser() == null || portfolio.getStock() == null) {
            throw new IllegalArgumentException("Portfolio, user, and stock cannot be null");
        }

        // Manage account balance
        Account account = portfolio.getAccount();
        if (account != null) {
            double newAvailableBalance = account.getAvailableBalance() - portfolio.getInvestment();
            if (newAvailableBalance < 0) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            account.setAvailableBalance(newAvailableBalance);
            accountDAO.update(account); // Update account in the database
        }

        // Manage history
        History history = portfolio.getHistory();
        if (history != null) {
            double profitLoss = portfolio.getInvestment() - (portfolio.getQuantity() * portfolio.getBuyPrice());
            if (profitLoss > 0) {
                history.setProfit(profitLoss);
                history.setLoss(0.0);
            } else {
                history.setProfit(0.0);
                history.setLoss(-profitLoss);
            }
            historyDAO.update(history); // Update history in the database
        }

        portfolioDAO.save(portfolio);
    }

    // Get portfolios for a user
    public List<Portfolio> getPortfoliosByUserId(Long userId) {
        return portfolioDAO.findByUserId(userId);
    }

    // Calculate total investment for a user
    public double calculateTotalInvestment(Long userId) {
        List<Portfolio> portfolios = portfolioDAO.findByUserId(userId);
        return portfolios.stream()
                         .mapToDouble(Portfolio::getInvestment)
                         .sum();
    }

 // Update an existing portfolio entry
    public void updatePortfolio(Portfolio portfolio) {
        if (portfolio == null || portfolio.getId() == null) {
            throw new IllegalArgumentException("Portfolio and Portfolio ID cannot be null");
        }

        // Fetch the existing portfolio from the database
        Portfolio existingPortfolio = portfolioDAO.findById(portfolio.getId());
        if (existingPortfolio == null) {
            throw new IllegalArgumentException("Portfolio not found");
        }

        // Manage account balance
        Account account = portfolio.getAccount();
        if (account != null) {
            double oldInvestment = existingPortfolio.getInvestment();
            double newInvestment = portfolio.getInvestment();
            double balanceAdjustment = newInvestment - oldInvestment;
            if (account.getAvailableBalance() < balanceAdjustment) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            account.setAvailableBalance(account.getAvailableBalance() - balanceAdjustment);
            accountDAO.update(account);
        }

        // Manage history
        History history = portfolio.getHistory();
        if (history != null) {
            updateHistoryForPortfolio(portfolio, history);
            historyDAO.update(history);
        }

        portfolioDAO.update(portfolio);
    }

    // Delete a portfolio entry
    public void deletePortfolio(Long portfolioId) {
        if (portfolioId == null) {
            throw new IllegalArgumentException("Portfolio ID cannot be null");
        }

        // Fetch the portfolio from the database
        Portfolio portfolio = portfolioDAO.findById(portfolioId);
        if (portfolio == null) {
            throw new IllegalArgumentException("Portfolio not found");
        }

        // Manage account balance
        Account account = portfolio.getAccount();
        if (account != null) {
            account.setAvailableBalance(account.getAvailableBalance() + portfolio.getInvestment());
            accountDAO.update(account);
        }

        // Optionally, you might want to manage history on deletion

        portfolioDAO.delete(portfolio);
    }

    // Helper method to update history for a portfolio
    private void updateHistoryForPortfolio(Portfolio portfolio, History history) {
        double profitLoss = portfolio.getInvestment() - (portfolio.getQuantity() * portfolio.getBuyPrice());
        if (profitLoss > 0) {
            history.setProfit(profitLoss);
            history.setLoss(0.0);
        } else {
            history.setProfit(0.0);
            history.setLoss(-profitLoss);
        }
    }

}