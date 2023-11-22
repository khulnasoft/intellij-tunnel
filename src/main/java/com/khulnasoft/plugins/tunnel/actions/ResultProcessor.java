package com.khulnasoft.plugins.tunnel.actions;

import com.khulnasoft.plugins.tunnel.model.Findings;
import com.khulnasoft.plugins.tunnel.ui.TunnelWindow;
import com.khulnasoft.plugins.tunnel.ui.notify.TunnelNotificationGroup;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;

import java.io.File;
import java.io.IOException;

/**
 * ResultProcessor takes the results finding and unmarshalls to object
 * Then updates the findings window
 */
public class ResultProcessor {

    public static void updateResults(Project project, File resultFile) {

        Findings findings;
        try {
            ObjectMapper findingsMapper = new ObjectMapper();
            findingsMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
            findingsMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            findings = findingsMapper.readValue(resultFile, Findings.class);

        } catch (IOException e) {
            TunnelNotificationGroup.notifyError(project, String.format("Failed to deserialize the results file. %s", e.getLocalizedMessage()));
            return;
        }

        // redraw the explorer with the updated content
        final ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Tunnel Findings");
        final Content content = toolWindow.getContentManager().getContent(0);
        final TunnelWindow TunnelWindow = (TunnelWindow) content.getComponent();
        TunnelWindow.updateFindings(findings);
        TunnelWindow.redraw();
    }

}
