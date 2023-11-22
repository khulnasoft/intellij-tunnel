package com.khulnasoft.plugins.tunnel.ui.treenodes;

import com.khulnasoft.plugins.tunnel.icons.TunnelIcons;
import com.khulnasoft.plugins.tunnel.model.Misconfiguration;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

class MisconfigurationTreeNode extends DefaultMutableTreeNode implements TunnelTreeNode {

    public final Misconfiguration misconfiguration;

    public MisconfigurationTreeNode(Misconfiguration misconfiguration) {
        super(misconfiguration);
        this.misconfiguration = misconfiguration;
    }

    @Override
    public Icon getIcon() {
        return TunnelIcons.Tunnel;
    }

    @Override
    public String getTitle() {
        return this.misconfiguration.id;
    }

    @Override
    public String getTooltip() {
        return this.misconfiguration.description;
    }
}
