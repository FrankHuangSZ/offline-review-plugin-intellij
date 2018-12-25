package com.idevcod.model;

import com.google.common.base.Joiner;
import com.idevcod.constant.TableHeader;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            case 0:
                return comment.getCategory();
            case 1:
                return comment.getLevel();
            case 2:
                return comment.getDetail();
            case 3:
                return comment.getFileName();
            case 4:
                return comment.getFullPath();
        }

        return "";
    }

    public String getColumnName(int col) {
        return TableHeader.TABLE_HEADER[col];
    }

    public void addRow(Comment comment) {
        comments.add(0, comment);

        fireTableRowsInserted(0, 0);
    }

    public void removeRow(int rowIndex) {
        comments.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void removeAll() {
        int totalRow = comments.size();

        comments.clear();
        fireTableRowsDeleted(0, totalRow - 1);
    }

    public Comment getComment(int index) {
        return comments.get(index);
    }

    public void exportComments(String projectName) {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Save Code Review result");
//        jfc.setFileSelectionMode(JFileChooser);

        String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = projectName + "-CodeReview-" + dateTime + ".csv";
        jfc.setSelectedFile(new File(fileName));

        int flag = jfc.showSaveDialog(null);
        if (flag == JFileChooser.APPROVE_OPTION) {
            writeCSVFile(jfc.getSelectedFile().getAbsolutePath());
        }
    }

    public void importComments(String projectName) {
        JOptionPane.showMessageDialog(null, "Coming soon");
    }


    public void writeCSVFile(String fileName) {
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            String titles = Joiner.on(",").join(TableHeader.TABLE_HEADER);
            out.write((titles+System.lineSeparator()).getBytes());
            
            for (Comment comment : comments) {
                String line = Joiner.on(",").join(comment.getCategory(), comment.getLevel(), comment.getDetail(), comment.getFileName(), comment.getFullPath());
                out.write((line + System.lineSeparator()).getBytes());
            }
            out.close();
        } catch (IOException e2) {
            JOptionPane.showMessageDialog(null, "Save Failed:" + e2.getMessage());
            e2.printStackTrace();
        }
    }
}
