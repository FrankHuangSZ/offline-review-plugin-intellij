package com.idevcod.ui;

import com.idevcod.model.ReviewTableModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.intellij.ui.table.JBTable;

public class WindowFactory implements ToolWindowFactory {

    private JBTable reviewTable;
    private JScrollPane reviewScrollPanel;
    private ToolWindow myToolWindow;
    private JPanel myToolWindowContent;
    private JPanel functionPanel;
    private JButton addBtn;
    private JButton removeBtn;

    public WindowFactory() {
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        myToolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(myToolWindowContent, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    private void createUIComponents() {
        reviewTable = new JBTable(new ReviewTableModel());
    }
}
