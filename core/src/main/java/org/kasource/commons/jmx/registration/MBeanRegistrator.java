package org.kasource.commons.jmx.registration;

import javax.management.ObjectName;

public interface MBeanRegistrator {
    void register(Object object);
    
    boolean unregister(ObjectName objectName);
    
    boolean unregister(Object object);
}
