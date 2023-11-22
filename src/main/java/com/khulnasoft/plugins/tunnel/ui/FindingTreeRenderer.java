package com.khulnasoft.plugins.tunnel.ui;

import com.khulnasoft.plugins.tunnel.ui.treenodes.TunnelTreeNode;
import com.intellij.ui.JBColor;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

class FindingTreeRenderer extends DefaultTreeCellRenderer {

    public FindingTreeRenderer() {
    }

    public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {

        super.getTreeCellRendererComponent(
                tree, value, sel,
                expanded, leaf, row,
                hasFocus);

        if (value instanceof TunnelTreeNode) {
            TunnelTreeNode node = (TunnelTreeNode) value;
            setIcon(node.getIcon());
            setText(node.getTitle());
            setBackgroundNonSelectionColor(JBColor.PanelBackground);
            setToolTipText(node.getTooltip());
        }
        return this;
    }

}
