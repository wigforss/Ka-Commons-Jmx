package org.kasource.commons.jmx.cdi.example;

import javax.enterprise.context.ApplicationScoped;

import org.kasource.cdi.extension.eager.Eager;
import org.kasource.commons.jmx.annotation.JmxBean;

@ApplicationScoped @Eager
@JmxBean(objectName="Test:name=Eager")
public class EagerOne {
    public EagerOne() {
        System.out.println("EagerOne");
    }
}
