package com.idevcod.service;

import com.intellij.ui.table.JBTable;

public class ReviewWindowService {
    private JBTable reviewTable;

    public JBTable getReviewTable() {
        return reviewTable;
    }

    public void setReviewTable(JBTable reviewTable) {
        this.reviewTable = reviewTable;
    }
}
