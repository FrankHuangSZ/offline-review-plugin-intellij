package com.idevcod.ui;

import javax.swing.*;

public class CommentPanel {
    private JPanel commitPanel;
    private JLabel categoriesLabel;
    private JComboBox categoriesField;
    private JLabel levelLabel;
    private JComboBox levelField;
    private JTextArea detailArea;
    private JLabel descriptionLabel;
    private JScrollPane descriptionScrollPanl;


    public JComponent getPanel() {
        return commitPanel;
    }

    public String getCategory() {
        return categoriesField.getSelectedItem().toString().trim();
    }

    public String getDetail() {
        return detailArea.getText().trim();
    }

    public String getLevel() {
        return levelField.getSelectedItem().toString();
    }
}
