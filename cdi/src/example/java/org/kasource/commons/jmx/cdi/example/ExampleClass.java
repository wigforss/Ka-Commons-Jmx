package org.kasource.commons.jmx.cdi.example;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.kasource.commons.jmx.annotation.JmxBean;
import org.kasource.commons.jmx.notification.NotificationDispatcher;

@ApplicationScoped
@JmxBean(objectName="Test:name=Example")
public class ExampleClass {

    @Inject
    private NotificationDispatcher notificationDispatcher;
    
    
    @PostConstruct
    public void initialize() {
        notificationDispatcher.toString();
    }
}
