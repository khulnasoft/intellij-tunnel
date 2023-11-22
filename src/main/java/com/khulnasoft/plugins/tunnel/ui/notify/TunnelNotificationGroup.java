package com.khulnasoft.plugins.tunnel.ui.notify;

import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

public class TunnelNotificationGroup {
    private static final NotificationGroup NOTIFICATION_GROUP = NotificationGroup.findRegisteredGroup("Tunnel Notifications");


    public static void notifyError(@Nullable Project project, String content) {
        notify(project, content, NotificationType.ERROR);
    }

    public static void notifyWarning(@Nullable Project project, String content) {
        notify(project, content, NotificationType.WARNING);
    }

    public static void notifyInformation(@Nullable Project project, String content) {
        notify(project, content, NotificationType.INFORMATION);
    }

    private static void notify(@Nullable Project project, String content, NotificationType notificationType) {
        NOTIFICATION_GROUP.createNotification(content, notificationType).notify(project);
    }
}
