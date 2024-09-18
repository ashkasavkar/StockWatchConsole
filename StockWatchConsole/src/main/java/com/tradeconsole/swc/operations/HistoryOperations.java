package com.tradeconsole.swc.operations;

import com.tradeconsole.swc.entity.History;
import com.tradeconsole.swc.service.HistoryService;


import java.util.List;

public class HistoryOperations {
    private HistoryService historyService;

    public HistoryOperations(HistoryService historyService) {
        this.historyService = historyService;
    }

    public void addHistory(History history) {
        historyService.addHistory(history);
    }

    public List<History> getHistoryByUserId(Long userId) {
        return historyService.getHistoryByUserId(userId);
    }
}