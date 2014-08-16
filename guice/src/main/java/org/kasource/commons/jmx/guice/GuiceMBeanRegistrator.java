package org.kasource.commons.jmx.guice;

import java.util.Set;

import org.kasource.commons.bean.BeanResolver;
import org.kasource.commons.jmx.registration.MBeanRegistratorImpl;
import org.kasource.commons.jmx.registration.MBeanServerLookup;

import com.google.inject.Inject;



public class GuiceMBeanRegistrator extends MBeanRegistratorImpl {

    public GuiceMBeanRegistrator() {
        
    }
    
    @Inject
    public void initialize(BeanResolver beanResolver) {
        Set<MBeanServerLookup> serverLookups = beanResolver.getBeans(MBeanServerLookup.class);
        if (!serverLookups.isEmpty()) {
            MBeanServerLookup serverLookup = serverLookups.iterator().next();
            setServerLookup(serverLookup);
        }
        initialize();
    }
}
