package com.khulnasoft.plugins.tunnel.ui;

import com.khulnasoft.plugins.tunnel.model.Finding;
import com.khulnasoft.plugins.tunnel.model.Findings;
import com.khulnasoft.plugins.tunnel.model.Location;
import com.khulnasoft.plugins.tunnel.model.Result;
import com.khulnasoft.plugins.tunnel.ui.notify.TunnelNotificationGroup;
import com.khulnasoft.plugins.tunnel.ui.treenodes.FileTreeNode;
import com.khulnasoft.plugins.tunnel.ui.treenodes.LocationTreeNode;
import com.khulnasoft.plugins.tunnel.ui.treenodes.SecretTreeNode;
import com.khulnasoft.plugins.tunnel.ui.treenodes.VulnerabilityTreeNode;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.ui.JBColor;
import com.intellij.ui.ScrollPaneFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.Tree;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TunnelWindow extends SimpleToolWindowPanel {

    private final Project project;
    private final FindingsHelper findingsHelper;
    private Tree root;


    public TunnelWindow(Project project) {
        super(false, true);

        this.setBackground(JBColor.PanelBackground);
        this.project = project;
        this.findingsHelper = new FindingsHelper();
        configureToolbar();
    }

    private void configureToolbar() {
        ActionManager actionManager = ActionManager.getInstance();

        DefaultActionGroup actionGroup = new DefaultActionGroup("ACTION_GROUP", false);

        actionGroup.add(actionManager.getAction("com.khulnasoft.plugins.tunnel.actions.RunScannerAction"));
        actionGroup.add(actionManager.getAction("com.khulnasoft.plugins.tunnel.actions.ClearResultsAction"));
        actionGroup.add(actionManager.getAction("com.khulnasoft.plugins.tunnel.actions.ShowTunnelSettingsAction"));

        ActionToolbar actionToolbar = actionManager.createActionToolbar("ACTION_TOOLBAR", actionGroup, true);
        actionToolbar.setOrientation(SwingConstants.VERTICAL);
        this.setToolbar(actionToolbar.getComponent());
    }

    @Override
    public @Nullable JComponent getContent() {
        return this.getComponent();
    }


    public void updateFindings(Findings findings) {
        if (findings == null) {
            this.root = null;
            return;
        }
        List<FileTreeNode> fileNodes = new ArrayList<>();
        findings.setBasePath(project.getBasePath());
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Findings by type");


        findings.results.forEach(f -> {
            if (f.misconfigurations != null && f.misconfigurations.size() > 0
                    || f.vulnerabilities != null && f.vulnerabilities.size() > 0
                    || f.secrets != null && f.secrets.size() > 0) {
                addOrUpdateTreeNode(f, fileNodes);
            }
        });

        fileNodes.forEach(rootNode::add);
        this.root = new Tree(rootNode);
        root.putClientProperty("JTree.lineStyle", "Horizontal");
        root.setRootVisible(false);
        root.setCellRenderer(new FindingTreeRenderer());
        root.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                doMouseClicked(me);
            }
        });
    }

    private void addOrUpdateTreeNode(Result finding, List<FileTreeNode> fileNodes) {
        var match = fileNodes.stream().filter(r -> r.getTarget().equals(finding.target)).findFirst();
        if (match.isPresent()) {
            match.get().update(finding);
            return;
        }

        FileTreeNode f = new FileTreeNode(finding);
        fileNodes.add(f);
    }


    void doMouseClicked(MouseEvent me) {
        Object lastSelectedNode = root.getLastSelectedPathComponent();
        if (lastSelectedNode == null) {
            return;
        }
        if (lastSelectedNode instanceof LocationTreeNode) {
            LocationTreeNode node = (LocationTreeNode) lastSelectedNode;
            Location findingLocation = node.getLocation();
            if (findingLocation == null) {
                return;
            }
            this.findingsHelper.setMisconfiguration(node.getMisconfiguration(), findingLocation.Filename);
            openFileLocation(findingLocation);
        } else if (lastSelectedNode instanceof VulnerabilityTreeNode) {
            VulnerabilityTreeNode node = (VulnerabilityTreeNode) lastSelectedNode;
            Location findingLocation = node.getLocation();
            if (findingLocation == null) {
                return;
            }
            this.findingsHelper.setVulnerability(node.getVulnerability(), findingLocation.Filename);
            openFileLocation(findingLocation);
        } else if (lastSelectedNode instanceof SecretTreeNode) {
            SecretTreeNode node = (SecretTreeNode) lastSelectedNode;
            this.findingsHelper.setSecret(node.getSecret(), node.getLocation().Filename);
            openFileLocation(node.getLocation());
        }
    }

    private void openFileLocation(Location findingLocation) {
        if (findingLocation == null) {
            return;
        }
        VirtualFile file = VirtualFileManager.getInstance().refreshAndFindFileByNioPath(Paths.get(project.getBasePath(), findingLocation.Filename));
        if (file == null || !file.exists()) {
            TunnelNotificationGroup.notifyInformation(project, String.format("File %s cannot be opened", findingLocation.Filename));
            return;
        }
        OpenFileDescriptor ofd = new OpenFileDescriptor(project, file, findingLocation.StartLine - 1, 0);

        Editor editor = FileEditorManager.getInstance(project).openTextEditor(ofd, true);
        if (editor == null) {
            return;
        }
        editor.getSelectionModel()
                .setBlockSelection(new LogicalPosition(findingLocation.StartLine - 1, 0),
                        new LogicalPosition(findingLocation.EndLine - 1, 1000));

    }

    public void redraw() {
        this.removeAll();
        if (this.root != null) {
            updateView();
        }

        configureToolbar();
        this.validate();
        this.repaint();
    }

    private void updateView() {
        JSplitPane splitPane = new JSplitPane(0);
        splitPane.setDividerSize(2);
        splitPane.add(new JBScrollPane(this.root));
        splitPane.add( new JBScrollPane(this.findingsHelper));
        this.add(splitPane);
    }
}
