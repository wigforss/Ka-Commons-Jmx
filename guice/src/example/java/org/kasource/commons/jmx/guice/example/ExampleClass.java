package org.kasource.commons.jmx.guice.example;

import org.kasource.commons.jmx.notification.NotificationDispatcher;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ExampleClass {

    @Inject
    private NotificationDispatcher notificationDispatcher;
}
