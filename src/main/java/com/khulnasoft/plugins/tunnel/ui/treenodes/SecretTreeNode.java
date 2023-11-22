package com.khulnasoft.plugins.tunnel.ui.treenodes;

import com.khulnasoft.plugins.tunnel.icons.TunnelIcons;
import com.khulnasoft.plugins.tunnel.model.Location;
import com.khulnasoft.plugins.tunnel.model.Secret;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class SecretTreeNode extends DefaultMutableTreeNode implements TunnelTreeNode {

    public final Secret secret;
    public final String filepath;

    public SecretTreeNode(Secret secret, String filepath) {
        super(secret);
        this.secret = secret;
        this.filepath = filepath;
    }

    @Override
    public Icon getIcon() {
        return TunnelIcons.Secret;
    }

    @Override
    public String getTitle() {
        return this.secret.ruleID   ;
    }

    @Override
    public String getTooltip() {
        return this.secret.title;
    }

    public Secret getSecret() {
        return secret;
    }

    public Location getLocation() {

        return new Location(filepath, secret.startLine, secret.endLine);
    }
}
