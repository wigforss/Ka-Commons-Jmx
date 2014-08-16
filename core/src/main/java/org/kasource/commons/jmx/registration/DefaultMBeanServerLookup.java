package org.kasource.commons.jmx.registration;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

public class DefaultMBeanServerLookup implements MBeanServerLookup {

    @Override
    public MBeanServer getMBeanServer() {
        return ManagementFactory.getPlatformMBeanServer();
    }

}
