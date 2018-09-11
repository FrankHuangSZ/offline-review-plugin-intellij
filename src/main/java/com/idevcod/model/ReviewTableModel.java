package com.idevcod.model;

import com.idevcod.constant.TableHeader;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ReviewTableModel extends AbstractTableModel {
    private List<Comment> comments = new ArrayList<>();

    @Override
    public int getRowCount() {
        return comments.size();
    }

    @Override
    public int getColumnCount() {
        return TableHeader.TABLE_HEADER.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Comment comment = comments.get(rowIndex);
        switch (columnIndex) {
            case 1:
                return comment.getFullPath();
            case 2:
                return comment.getLocation().toString();
            case 3:
                return comment.getDescription();
        }

        return "";
    }

    public String getColumnName(int col) {
        return TableHeader.TABLE_HEADER[col];
    }
}
