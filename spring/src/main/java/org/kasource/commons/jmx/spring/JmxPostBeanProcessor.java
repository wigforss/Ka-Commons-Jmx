package org.kasource.commons.jmx.spring;

import org.kasource.commons.jmx.annotation.JmxBean;
import org.kasource.commons.jmx.registration.MBeanRegistrator;
import org.kasource.commons.reflection.util.AnnotationUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class JmxPostBeanProcessor implements BeanPostProcessor {

    private MBeanRegistrator registrator;
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {      
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (AnnotationUtils.isAnnotationPresent(bean.getClass(), JmxBean.class)) {
            registrator.register(bean);
        }
        return bean;
    }

    /**
     * @param registrator the registrator to set
     */
    public void setRegistrator(MBeanRegistrator registrator) {
        this.registrator = registrator;
    }

}
