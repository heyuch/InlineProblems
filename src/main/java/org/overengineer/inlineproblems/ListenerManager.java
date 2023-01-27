package org.overengineer.inlineproblems;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.ProjectManager;
import org.overengineer.inlineproblems.listeners.MarkupModelProblemListener;


public class ListenerManager {
    private final Logger logger;

    public ListenerManager() {
        logger = Logger.getInstance(ListenerManager.class);
    }

    public void installMarkupModelListenerOnAllProjects() {
        ProjectManager manager = ProjectManager.getInstanceIfCreated();
        if (manager != null) {
            for (var project : manager.getOpenProjects()) {
                FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
                for (var editor : fileEditorManager.getAllEditors()) {
                    if (editor instanceof TextEditor) {
                        MarkupModelProblemListener.setup((TextEditor) editor);
                        logger.debug("Installing MarkupModelListener");
                    }
                }
            }
        }
    }
}
