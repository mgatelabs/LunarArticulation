package com.mgatelabs.lunar.utils.fields;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Michael Glen Fuller Jr on 10/26/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EditableAnnotation {
    String title();
    EditableTypes type();
    int length() default 0;
    int minValue() default 0;
    int maxValue() default 0;
    String regex() default "";
}
