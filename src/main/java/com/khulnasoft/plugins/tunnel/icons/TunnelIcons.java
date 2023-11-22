package com.khulnasoft.plugins.tunnel.icons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;


public interface TunnelIcons {
    Icon Tunnel = IconLoader.getIcon("/icons/tunnel.png", TunnelIcons.class);
    Icon Critical = IconLoader.getIcon("/icons/critical.svg", TunnelIcons.class);
    Icon High = IconLoader.getIcon("/icons/high.svg", TunnelIcons.class);
    Icon Medium = IconLoader.getIcon("/icons/medium.svg", TunnelIcons.class);
    Icon Low = IconLoader.getIcon("/icons/low.svg", TunnelIcons.class);

    Icon Cargo = IconLoader.getIcon("/icons/cargo.svg", TunnelIcons.class);
    Icon CloudFormation = IconLoader.getIcon("/icons/cloudformation.svg", TunnelIcons.class);
    Icon Docker = IconLoader.getIcon("/icons/dockerfile.svg", TunnelIcons.class);
    Icon Gem = IconLoader.getIcon("/icons/ruby.svg", TunnelIcons.class);
    Icon Go = IconLoader.getIcon("/icons/go.svg", TunnelIcons.class);
    Icon Java = IconLoader.getIcon("/icons/java.svg", TunnelIcons.class);
    Icon Javascript = IconLoader.getIcon("/icons/javascript.svg", TunnelIcons.class);
    Icon Node = IconLoader.getIcon("/icons/node.svg", TunnelIcons.class);
    Icon Npm = IconLoader.getIcon("/icons/npm.svg", TunnelIcons.class);
    Icon Nuget = IconLoader.getIcon("/icons/nuget.svg", TunnelIcons.class);
    Icon Python = IconLoader.getIcon("/icons/python.svg", TunnelIcons.class);

    Icon Secret = IconLoader.getIcon("/icons/secret.svg", TunnelIcons.class);
    Icon Terraform = IconLoader.getIcon("/icons/terraform.svg", TunnelIcons.class);
    Icon Yaml = IconLoader.getIcon("/icons/yaml.svg", TunnelIcons.class);
    Icon Yarn = IconLoader.getIcon("/icons/yarn.svg", TunnelIcons.class);

    Icon Unidentified = IconLoader.getIcon("/icons/tunnel.svg", TunnelIcons.class);
}
