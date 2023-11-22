package com.khulnasoft.plugins.tunnel.ui.treenodes;

import com.khulnasoft.plugins.tunnel.model.Vulnerability;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

class VulnerablePackageTreeNode extends DefaultMutableTreeNode implements TunnelTreeNode {

    public final Vulnerability vulnerability;

    public VulnerablePackageTreeNode(Vulnerability vulnerability) {
        super(vulnerability);
        this.vulnerability = vulnerability;
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public String getTitle() {
        return this.vulnerability.pkgName;
    }

    @Override
    public String getTooltip() {
        return this.vulnerability.description;
    }
}
