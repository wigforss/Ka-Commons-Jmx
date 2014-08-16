package org.kasource.commons.jmx.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate a method with this annotation to
 * provide the ObjectName of this instance.
 * 
 * The method should be public taking no parameters and return a valid ObjectName as a String.
 * 
 * If the objectName attribute of the @JmxBean annotation
 * is not set then a method needs to annotated with this 
 * annotation in order to resolve an ObjectName.
 * 
 * @author rikardwi
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JmxObjectName {
  
}
