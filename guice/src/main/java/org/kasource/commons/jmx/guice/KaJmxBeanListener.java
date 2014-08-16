package org.kasource.commons.jmx.guice;

import java.util.LinkedList;
import java.util.Queue;

import org.kasource.commons.jmx.annotation.JmxBean;

import com.google.inject.Inject;
import com.google.inject.spi.InjectionListener;

public class KaJmxBeanListener implements InjectionListener<Object> {

    private volatile GuiceMBeanRegistrator mBeanRegistrator;
    private Queue<Object> candidates = new LinkedList<Object>();
    
    @Override
    public void afterInjection(Object object) {
        if (mBeanRegistrator != null) {
            
        } else {
            candidates.add(object);
        }
    }
    
    @Inject
    public void initilize(GuiceMBeanRegistrator mBeanRegistrator) {
        while (!candidates.isEmpty()) {
            Object object = candidates.poll();
            if (object.getClass().isAnnotationPresent(JmxBean.class)) {
                mBeanRegistrator.register(object);
            }
        }
        this.mBeanRegistrator = mBeanRegistrator;
    }

}
