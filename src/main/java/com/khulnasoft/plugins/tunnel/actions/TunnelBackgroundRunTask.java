package com.khulnasoft.plugins.tunnel.actions;

import com.khulnasoft.plugins.tunnel.settings.TunnelSettingState;
import com.khulnasoft.plugins.tunnel.ui.notify.TunnelNotificationGroup;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ScriptRunnerUtil;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

class TunnelBackgroundRunTask extends Task.Backgroundable implements Runnable {

    private final Project project;
    private final File resultFile;
    private final BiConsumer<Project, File> callback;

    public TunnelBackgroundRunTask(Project project, File resultFile, BiConsumer<Project, File> updateResults) {
        super(project, "Running Tunnel", false);
        this.project = project;
        this.resultFile = resultFile;
        this.callback = updateResults;
    }

    @Override
    public void run(@NotNull ProgressIndicator indicator) {
        this.run();
    }

    @Override
    public void run() {
        String severities = getRequiredSeverities();

        List<String> commandParts = new ArrayList<>();
        commandParts.add(TunnelSettingState.getInstance().TunnelPath);
        commandParts.add("fs");

        String requiredChecks = "config,vuln";
        if (TunnelSettingState.getInstance().SecretScanning) {
            requiredChecks = String.format("%s,secret", requiredChecks);
        }

        commandParts.add(String.format("--security-checks=%s", requiredChecks));
        commandParts.add(String.format("--severity=%s", severities));

        if (TunnelSettingState.getInstance().OfflineScan) {
            commandParts.add("--offline-scan");
        }

        if (TunnelSettingState.getInstance().IgnoreUnfixed) {
            commandParts.add("--ignore-unfixed");
        }
        if (TunnelSettingState.getInstance().ServerEnabled && !TunnelSettingState.getInstance().RemoteServerURL.isEmpty()) {
            commandParts.add(String.format("--server=%s", TunnelSettingState.getInstance().RemoteServerURL));
        }

        commandParts.add("--format=json");
        commandParts.add(String.format("--output=%s", resultFile.getAbsolutePath()));
        commandParts.add(this.project.getBasePath());

        GeneralCommandLine commandLine = new GeneralCommandLine(commandParts);

        Process process;
        try {
            process = commandLine.createProcess();
        } catch (ExecutionException e) {
            TunnelNotificationGroup.notifyError(project, e.getLocalizedMessage());
            return;
        }

        TunnelNotificationGroup.notifyInformation(project, commandLine.getCommandLineString());
        OSProcessHandler handler = new OSProcessHandler(process, commandLine.getCommandLineString());

        try {
            ScriptRunnerUtil.getProcessOutput(handler,
                    ScriptRunnerUtil.STDOUT_OR_STDERR_OUTPUT_KEY_FILTER,
                    100000000);
            TunnelNotificationGroup.notifyInformation(project, "Tunnel run completed, updating results");
            SwingUtilities.invokeLater(() -> {
                callback.accept(this.project, this.resultFile);
            });
        } catch (ExecutionException e) {
            TunnelNotificationGroup.notifyError(project, e.getLocalizedMessage());
        }

    }

    private String getRequiredSeverities() {
        List<String> requiredSeverities = new ArrayList<>();

        if (TunnelSettingState.getInstance().CriticalSeverity) {
            requiredSeverities.add("CRITICAL");
        }
        if (TunnelSettingState.getInstance().HighSeverity) {
            requiredSeverities.add("HIGH");
        }
        if (TunnelSettingState.getInstance().MediumSeverity) {
            requiredSeverities.add("MEDIUM");
        }
        if (TunnelSettingState.getInstance().LowSeverity) {
            requiredSeverities.add("LOW");
        }
        if (TunnelSettingState.getInstance().UnknownSeverity) {
            requiredSeverities.add("UNKNOWN");
        }

        return String.join(",", requiredSeverities);
    }
}
