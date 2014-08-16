package org.kasource.commons.jmx.spring;


import java.util.concurrent.Executor;

import org.kasource.commons.jmx.notification.NotificationDispatcher;
import org.kasource.commons.jmx.notification.NotificationDispatcherImpl;
import org.kasource.commons.jmx.registration.MBeanRegistrator;
import org.kasource.commons.jmx.registration.MBeanRegistratorImpl;
import org.kasource.commons.jmx.registration.MBeanServerLookup;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KaCommonsJmxConfiguration {

    public static final String NOTIFICATION_EXECUTOR_BEAN = "ka-Jmx-NotificationExecutor";
    public static final String POST_BEAN_PROCESSOR_BEAN = "ka-Jmx-PostBeanProcessor";
    
    @Autowired
    @Bean
    public MBeanRegistrator getMBeanRegistrator(ApplicationContext applicationContext) {
        MBeanRegistratorImpl registrator = new MBeanRegistratorImpl();
        try {
            MBeanServerLookup serverLookup = applicationContext.getBean(MBeanServerLookup.class);
            registrator.setServerLookup(serverLookup);
        } catch (NoSuchBeanDefinitionException e) {
            // Ignore
        }
        registrator.initialize();
        return registrator;
    }
    
    @Autowired
    @Bean(name = POST_BEAN_PROCESSOR_BEAN)
    public JmxPostBeanProcessor getBeanPostProcessor(MBeanRegistrator registrator) {
        JmxPostBeanProcessor jmxPostBeanProcessor = new JmxPostBeanProcessor();
        jmxPostBeanProcessor.setRegistrator(registrator);
        return jmxPostBeanProcessor;
        
    }
    
    @Autowired
    @Bean
    public NotificationDispatcher getNotificationDispatcher(ApplicationContext applicationContext) {
        Executor executor = null;
        Class<?> beanClass = null;
        try {
             Object bean =  applicationContext.getBean(NOTIFICATION_EXECUTOR_BEAN);
             beanClass = bean.getClass();
             executor = (Executor) bean;
            
        } catch (BeansException e) {
            // Ignore
        } catch (ClassCastException e) {
           throw new BeanNotOfRequiredTypeException(NOTIFICATION_EXECUTOR_BEAN, Executor.class, beanClass);
        }
        if (executor != null) {
            return new NotificationDispatcherImpl(executor);
        } else {
            return new NotificationDispatcherImpl();
        }
    }
}
