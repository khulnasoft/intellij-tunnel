package com.khulnasoft.plugins.tunnel.ui.treenodes;

import com.khulnasoft.plugins.tunnel.icons.TunnelIcons;

import javax.swing.*;

public class IconHelper {

    protected static Icon getFileIcon(String type) {
        switch (type) {
            case "cargo":
                return TunnelIcons.Cargo;
            case "cloudformation":
                return TunnelIcons.CloudFormation;
            case "dockerfile":
                return TunnelIcons.Docker;
            case "gemspec":
                return TunnelIcons.Gem;
            case "gobinary":
            case "gomod":
                return TunnelIcons.Go;
            case "jar":
            case "pom":
                return TunnelIcons.Java;
            case "javascript":
                return TunnelIcons.Javascript;
            case "node":
                return TunnelIcons.Node;
            case "npm":
                return TunnelIcons.Npm;
            case "nuget":
                return TunnelIcons.Nuget;
            case "pip":
            case "pipenv":
            case "python-pkg":
                return TunnelIcons.Python;
            case "secret":
                return TunnelIcons.Secret;
            case "terraform":
                return TunnelIcons.Terraform;
            case "yarn":
                return TunnelIcons.Yarn;
            case "yaml":
                return TunnelIcons.Yaml;
            default:
                return TunnelIcons.Tunnel;
        }
    }
}
