package com.idevcod.model;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.idevcod.constant.TableHeader;
import com.intellij.openapi.diagnostic.Logger;

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
    private static final Logger LOGGER = Logger.getInstance(ReviewTableModel.class);
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

    //export file
    public void exportComments(String projectPath, String projectName) {

        String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = projectName + "-CodeReview-" + dateTime + ".csv";

        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Save Code Review result");
        jfc.setSelectedFile(new File(fileName));

        int flag = jfc.showSaveDialog(null);
        if (flag == JFileChooser.APPROVE_OPTION) {
            writeCSVFile(jfc.getSelectedFile().getAbsolutePath(), projectPath);
        }
    }

    public void importComments(String projectPath) {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.setDialogTitle("Select Code Review result");
        int flag = jfc.showOpenDialog(null);
        if (flag == JFileChooser.APPROVE_OPTION) {
            readCSVFile(jfc.getSelectedFile().getAbsolutePath(), projectPath);
        }
    }


    public void writeCSVFile(String fileName, String projectPath) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            byte[] bs = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};   //new added
            fos.write(bs);

            String titles = Joiner.on(",").join(TableHeader.TABLE_HEADER);
            fos.write((titles + System.lineSeparator()).getBytes());

            for (Comment comment : comments) {
                String line = Joiner.on(",").join(comment.getCategory(), comment.getLevel(), comment.getDetail(), comment.getFileName(), comment.getFullPath().replace(projectPath, ""));
                fos.write((line + System.lineSeparator()).getBytes(Charsets.UTF_8));
            }
            fos.close();
            JOptionPane.showMessageDialog(null, "Export code review comments success");
        } catch (IOException e2) {
            String errorMsg = String.format("Export file[%s] exception \n[%s]", fileName, e2);
            JOptionPane.showMessageDialog(null, errorMsg);
        }
    }

    public void readCSVFile(String fileName, String projectPath) {
        try {
            boolean firstLine = true;
            List<String> lines = com.google.common.io.Files.readLines(new File(fileName), Charsets.UTF_8);
            for (String line : lines) {
                //skip title
                if (line.startsWith("Category")) {
                    continue;
                }

                //skip first line
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                Comment comment = getCommentFromString(line, projectPath);
                if (comment != null) {
                    addRow(comment);
                }
            }
            JOptionPane.showMessageDialog(null, "Import Success");
        } catch (IOException e) {
            String errorMsg = String.format("Import file[%s] exception \n[%s]", fileName, e);
            JOptionPane.showMessageDialog(null, errorMsg);
        }
    }

    private Comment getCommentFromString(String line, String projectPath) {
        if (Strings.isNullOrEmpty(line)) {
            return null;
        }

        List<String> fields = Splitter.on(",").splitToList(line);
        if (fields.size() != TableHeader.TABLE_HEADER.length) {
            return null;
        }

        Comment comment = new Comment();
        comment.setCategory(fields.get(0));
        comment.setLevel(fields.get(1));
        comment.setDetail(fields.get(2));
        String fn = fields.get(3);
        comment.setFileName(fn);
        String posArr[] = fn.split(" ");
        if (posArr.length == 2) {
            comment.setLocation(new Location(posArr[1]));
        }
        comment.setFullPath(projectPath + "/" + fields.get(4));


        return comment;
    }
}
