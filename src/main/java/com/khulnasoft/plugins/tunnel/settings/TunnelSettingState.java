package com.khulnasoft.plugins.tunnel.settings;


import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@State(
        name = "com.khulnasoft.plugins.tunnel.settings.TunnelSettingState",
        storages = @Storage("tunnel.xml")
)
public class TunnelSettingState implements PersistentStateComponent<TunnelSettingState> {

    public String TunnelPath = "tunnel";
    public boolean CriticalSeverity = true;
    public boolean HighSeverity = true;
    public boolean MediumSeverity = true;
    public boolean LowSeverity = true;
    public boolean UnknownSeverity = true;
    public boolean OfflineScan = false;
    public boolean IgnoreUnfixed = false;
    public boolean SecretScanning = false;
    public boolean ServerEnabled = false;
    public String RemoteServerURL ="";

    public static TunnelSettingState getInstance() {
        return ApplicationManager.getApplication().getService(TunnelSettingState.class);
    }

    @Nullable
    @Override
    public TunnelSettingState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull TunnelSettingState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}