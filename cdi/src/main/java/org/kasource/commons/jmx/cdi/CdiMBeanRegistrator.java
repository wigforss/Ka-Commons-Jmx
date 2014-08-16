package org.kasource.commons.jmx.cdi;


import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.kasource.commons.bean.BeanResolver;
import org.kasource.commons.cdi.extension.beanlisten.BeanEventDispatcher;
import org.kasource.commons.cdi.extension.beanlisten.annotation.OnBeanPostConstruct;
import org.kasource.commons.cdi.extension.beanlisten.annotation.OnBeanPreDestroy;
import org.kasource.commons.cdi.extension.eager.Eager;
import org.kasource.commons.jmx.annotation.JmxBean;
import org.kasource.commons.jmx.registration.MBeanRegistratorImpl;
import org.kasource.commons.jmx.registration.MBeanServerLookup;
import org.kasource.commons.reflection.util.AnnotationUtils;

@ApplicationScoped @Eager
public class CdiMBeanRegistrator extends MBeanRegistratorImpl {

    @Inject
    private BeanEventDispatcher eventDispatcher;
    
    @Inject
    private BeanResolver beanResolver;
    
    @PostConstruct
    protected void postContruct() {
        eventDispatcher.addBeanListener(this);
        Set<MBeanServerLookup> lookupBeans = beanResolver.getBeans(MBeanServerLookup.class);
        if (!lookupBeans.isEmpty()){
            MBeanServerLookup serverLookup = lookupBeans.iterator().next();
            setServerLookup(serverLookup);
        }
        initialize();
    }
    
    @OnBeanPostConstruct
    public void onBeanCreation(Object bean) {
        if (AnnotationUtils.isAnnotationPresent(bean.getClass(), JmxBean.class)) {
            register(bean);
        }
    }
    
    @OnBeanPreDestroy
    public void onBeanDestruction(Object bean) {
        if (AnnotationUtils.isAnnotationPresent(bean.getClass(), JmxBean.class)) {
            unregister(bean);
        }
    }
    
   
    
    
}
