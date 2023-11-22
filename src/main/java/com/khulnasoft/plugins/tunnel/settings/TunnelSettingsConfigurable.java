package com.khulnasoft.plugins.tunnel.settings;


import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Provides controller functionality for application settings.
 */
public class TunnelSettingsConfigurable implements Configurable {

    private TunnelSettingsComponent TunnelSettingsComponent;

    // A default constructor with no arguments is required because this implementation
    // is registered as an applicationConfigurable EP

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Tunnel: Settings";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return TunnelSettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        TunnelSettingsComponent = new TunnelSettingsComponent();
        return TunnelSettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        TunnelSettingState settings = TunnelSettingState.getInstance();
        boolean modified = !TunnelSettingsComponent.getTunnelPath().equals(settings.TunnelPath) ||
                !TunnelSettingsComponent.getCriticalSeverityRequired() == settings.CriticalSeverity ||
                !TunnelSettingsComponent.getHighSeverityRequired() == settings.HighSeverity ||
                !TunnelSettingsComponent.getMediumSeverityRequired() == settings.MediumSeverity||
                !TunnelSettingsComponent.getLowSeverityRequired() == settings.LowSeverity         ||
                !TunnelSettingsComponent.getUnknownSeverityRequired() == settings.UnknownSeverity ||
                !TunnelSettingsComponent.getOfflineScanRequired() == settings.OfflineScan ||
                !TunnelSettingsComponent.getShowOnlyFixed() == settings.IgnoreUnfixed ||
                !TunnelSettingsComponent.getSecretScanning() == settings.SecretScanning||
                !TunnelSettingsComponent.getServerEnabled() == settings.ServerEnabled||
                !TunnelSettingsComponent.getRemoteServerURL().equals(settings.RemoteServerURL)
                ;
        return modified;
    }

    @Override
    public void apply() {
        TunnelSettingState settings = TunnelSettingState.getInstance();
        settings.TunnelPath = TunnelSettingsComponent.getTunnelPath();
        settings.CriticalSeverity = TunnelSettingsComponent.getCriticalSeverityRequired();
        settings.HighSeverity = TunnelSettingsComponent.getHighSeverityRequired();
        settings.MediumSeverity = TunnelSettingsComponent.getMediumSeverityRequired();
        settings.LowSeverity = TunnelSettingsComponent.getLowSeverityRequired();
        settings.UnknownSeverity = TunnelSettingsComponent.getUnknownSeverityRequired();
        settings.OfflineScan = TunnelSettingsComponent.getOfflineScanRequired();
        settings.IgnoreUnfixed = TunnelSettingsComponent.getShowOnlyFixed();
        settings.SecretScanning = TunnelSettingsComponent.getSecretScanning();
        settings.ServerEnabled = TunnelSettingsComponent.getServerEnabled();
        settings.RemoteServerURL = TunnelSettingsComponent.getRemoteServerURL();
    }

    @Override
    public void reset() {
        TunnelSettingState settings = TunnelSettingState.getInstance();
        TunnelSettingsComponent.setTunnelPath(settings.TunnelPath);
        TunnelSettingsComponent.setCriticalSeverity(settings.CriticalSeverity);
        TunnelSettingsComponent.setHighSeverity(settings.HighSeverity);
        TunnelSettingsComponent.setMediumSeverity(settings.MediumSeverity);
        TunnelSettingsComponent.setLowSeverity(settings.LowSeverity);
        TunnelSettingsComponent.setUnknownSeverity(settings.UnknownSeverity);
        TunnelSettingsComponent.setOfflineScan(settings.OfflineScan);
        TunnelSettingsComponent.setIgnoreUnfixed(settings.IgnoreUnfixed);
        TunnelSettingsComponent.setSecretScanning(settings.SecretScanning);
        TunnelSettingsComponent.setServerEnabled(settings.ServerEnabled);
        TunnelSettingsComponent.setRemoteServerURL(settings.RemoteServerURL);

    }

    @Override
    public void disposeUIResources() {
        TunnelSettingsComponent = null;
    }

}
