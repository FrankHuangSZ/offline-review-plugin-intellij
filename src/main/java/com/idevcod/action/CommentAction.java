package com.idevcod.action;

import com.google.common.base.Strings;
import com.idevcod.model.Comment;
import com.idevcod.model.Location;
import com.idevcod.model.Position;
import com.idevcod.model.ReviewTableModel;
import com.idevcod.service.ReviewWindowService;
import com.idevcod.ui.CommentDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;


public class CommentAction extends AnAction {
    private static final Logger LOGGER = Logger.getInstance(CommentAction.class);

    @Override
    public void actionPerformed(AnActionEvent event) {
        CommentDialog commentDialog = new CommentDialog(event.getProject());
        commentDialog.show();
        if (commentDialog.getExitCode() != DialogWrapper.OK_EXIT_CODE) {
            return;
        }

        Comment comment = commentDialog.getComment();
        fillCommentProjectInfo(event, comment);

        ReviewTableModel reviewModel = getReviewModel(event.getProject());
        if (reviewModel == null) {
            LOGGER.error("reviewModel is null");
            return;
        }

        reviewModel.addRow(comment);
    }

    private void fillCommentProjectInfo(AnActionEvent event, Comment comment) {
        VirtualFile commentFile = event.getData(PlatformDataKeys.VIRTUAL_FILE);
        comment.setFullPath(commentFile.getCanonicalPath());
        comment.setFileName(commentFile.getName());

        Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        SelectionModel selectionModel = editor.getSelectionModel();
        LogicalPosition startPosition = editor.offsetToLogicalPosition(selectionModel.getSelectionStart());
        LogicalPosition endPosition = editor.offsetToLogicalPosition(selectionModel.getSelectionEnd());

        comment.setLocation(new Location(new Position(startPosition.line, startPosition.column),
            new Position(endPosition.line, endPosition.column)));
        //set file name
        String filename = commentFile.getName();
        filename += String.format(" %s", comment.getLocation().getLocation());
        comment.setFileName(filename);

        //set detail
        String selectTxt = selectionModel.getSelectedText();
        if (!Strings.isNullOrEmpty(selectTxt)) {
            String commentTxt = String.format("%s <= [%s]", comment.getDetail(), selectTxt);
            comment.setDetail(commentTxt);
        }
    }

    @Override
    public void update(AnActionEvent e) {
        //Get required data keys
        final Project project = e.getProject();
        final Editor editor = e.getData(CommonDataKeys.EDITOR);
        //Set visibility only in case of existing project and editor
        e.getPresentation().setVisible(project != null && editor != null);
    }

    private ReviewTableModel getReviewModel(Project project) {
        ReviewWindowService windowService = ServiceManager.getService(project, ReviewWindowService.class);
        return windowService.getReviewTableModel();
    }
}

