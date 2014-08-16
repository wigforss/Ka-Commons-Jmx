package org.kasource.commons.jmx.cdi;

import java.util.concurrent.Executor;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.kasource.commons.bean.BeanResolver;
import org.kasource.commons.jmx.notification.NotificationDispatcher;
import org.kasource.commons.jmx.notification.NotificationDispatcherImpl;



@ApplicationScoped
public class KaJmxConfiguration {
    private static final String NOTIFICATION_EXECUTOR_BEAN = "ka-Jmx-NotificationExecutor";
    
    @Inject
    private CdiMBeanRegistrator mBeanRegistrator;
    
    @Inject
    private BeanResolver beanResolver;
    
    @Produces @ApplicationScoped
    NotificationDispatcher getNotificationDispatcher() {
        NotificationDispatcherImpl dispatcher = null;
        try {
            Executor executor = beanResolver.getBean(NOTIFICATION_EXECUTOR_BEAN, Executor.class);
            dispatcher = new  NotificationDispatcherImpl(executor);
        } catch (Exception e) { 
            dispatcher = new NotificationDispatcherImpl();
        }
        
        mBeanRegistrator.register(dispatcher);
        return dispatcher;
    }
    
    
    
}
