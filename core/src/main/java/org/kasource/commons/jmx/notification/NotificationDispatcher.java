package org.kasource.commons.jmx.notification;

import javax.management.Notification;
import javax.management.NotificationEmitter;

public interface NotificationDispatcher extends NotificationEmitter {
    public void sendNotification(Notification notification);
    
    public void registerNotification(Class<? extends Notification> notificationClass);
}
