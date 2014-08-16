package org.kasource.commons.jmx.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate Notifications (classes extending javax.management.Notification) with 
 * this annotation to provide additional meta data such as name and description.
 * 
 * @author rikardwi
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JmxNotification {
    String name() default "";
    String description() default "";
}
