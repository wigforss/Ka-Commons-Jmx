package org.kasource.commons.jmx.guice.example;

import org.kasource.commons.jmx.guice.KaCommonsJmxModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceExample {
    public static void main(String[] args) throws InterruptedException {
        Injector injector = Guice.createInjector(new KaCommonsJmxModule());
        injector.getInstance(ExampleClass.class);
    }
}
