package com.khulnasoft.plugins.tunnel.ui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class TunnelWindowFactory implements ToolWindowFactory {

    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        TunnelWindow TunnelWindow = new TunnelWindow(project);
        ContentFactory contentFactory =  ApplicationManager.getApplication().getService(ContentFactory.class);
        Content content = contentFactory.createContent(TunnelWindow.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
