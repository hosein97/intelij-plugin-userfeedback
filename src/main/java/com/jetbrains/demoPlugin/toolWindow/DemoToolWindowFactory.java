package com.jetbrains.demoPlugin.toolWindow;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.jetbrains.demoPlugin.ui.MainPane;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class DemoToolWindowFactory implements ToolWindowFactory, DumbAware {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        SimpleToolWindowPanel toolWindowPanel = new SimpleToolWindowPanel(true, true);
        toolWindowPanel.setContent(new MainPane());

        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(toolWindowPanel, "", false);
        toolWindow.getContentManager().addContent(content);

    }
}
