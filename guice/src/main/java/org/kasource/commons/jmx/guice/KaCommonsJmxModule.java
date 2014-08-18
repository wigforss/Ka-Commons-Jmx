package org.kasource.commons.jmx.guice;



import java.util.concurrent.Executor;

import org.kasource.di.bean.BeanResolver;
import org.kasource.guice.bean.GuiceBeanResolver;
import org.kasource.guice.injection.listener.InjectionListenerRegister;
import org.kasource.guice.injection.listener.InjectionTypeListener;
import org.kasource.commons.jmx.notification.NotificationDispatcher;
import org.kasource.commons.jmx.notification.NotificationDispatcherImpl;
import org.kasource.commons.jmx.registration.MBeanRegistrator;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;


public class KaCommonsJmxModule  extends AbstractModule {
    private static final String NOTIFICATION_EXECUTOR_BEAN = "ka-Jmx-NotificationExecutor";
    
    @Override
    protected void configure() {
        InjectionTypeListener typeListener = new InjectionTypeListener(getInjectionListenerRegister());
        
        bindListener(Matchers.any(), typeListener); 
        bind(BeanResolver.class).to(GuiceBeanResolver.class);
        bind(MBeanRegistrator.class).to(GuiceMBeanRegistrator.class).asEagerSingleton();
        
    }
    
    private InjectionListenerRegister getInjectionListenerRegister() {
        InjectionListenerRegister register = new InjectionListenerRegister();
        register.addListener(new KaJmxBeanListener());
        return register;
    }
    
    @Provides @Singleton
    NotificationDispatcher getNotificationDispatcher(BeanResolver beanResolver) {
        NotificationDispatcherImpl dispatcher = null;
        try {
            Executor executor = beanResolver.getBean(NOTIFICATION_EXECUTOR_BEAN, Executor.class);
            dispatcher = new  NotificationDispatcherImpl(executor);
        } catch (Exception e) { 
            dispatcher = new NotificationDispatcherImpl();
        }
        
       // mBeanRegistrator.register(dispatcher);
        return dispatcher;
    }
    
    
}
