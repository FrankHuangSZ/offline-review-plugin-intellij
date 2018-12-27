package com.idevcod.service;

import com.idevcod.model.ReviewTableModel;

//@State(name = "code-review",additionalExportFile = "code-review.xml")
//@Storage(value = "code-review.xmlcode-review.xml")
public class ReviewWindowService /*implements PersistentStateComponent<ReviewTableModel>*/ {
    private final static Object LOCK = new Object();
    private ReviewTableModel reviewTable;

    public ReviewTableModel getReviewTableModel() {
        if (reviewTable == null) {
            synchronized (LOCK) {
                if (reviewTable == null) {
                    reviewTable = new ReviewTableModel();
                }
            }
        }

        return reviewTable;
    }

//    @Nullable
//    @Override
//    public ReviewTableModel getState() {
//        return reviewTable;
//    }
//
//    @Override
//    public void loadState(@NotNull ReviewTableModel reviewTableModel) {
//        XmlSerializerUtil.copyBean(reviewTableModel, reviewTable);
//    }
}
