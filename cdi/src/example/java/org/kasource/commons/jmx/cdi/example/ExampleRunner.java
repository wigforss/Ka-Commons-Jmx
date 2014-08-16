package org.kasource.commons.jmx.cdi.example;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.kasource.commons.jmx.notification.NotificationDispatcher;

public class ExampleRunner {

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        WeldContainer weld = new Weld().initialize();
        ExampleClass example = weld.instance().select(ExampleClass.class).get();
        System.out.println(example.toString());
        Thread.sleep(10000);
    }

}
