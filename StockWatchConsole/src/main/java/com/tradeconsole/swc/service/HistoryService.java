package com.tradeconsole.swc.service;

import java.util.List;

import com.tradeconsole.swc.dao.HistoryDAO;
import com.tradeconsole.swc.entity.History;

public class HistoryService {
    private HistoryDAO historyDAO = new HistoryDAO();

    // Add a new history entry
    public void addHistory(History history) {
        historyDAO.save(history);
    }

    // Get history records for a user
    public List<History> getHistoryByUserId(Long userId) {
        return historyDAO.findByUserId(userId);
    }

    // Get profit or loss for a specific history record
    public double getProfitOrLoss(Long historyId) {
        return historyDAO.getProfitOrLoss(historyId);
    }
}