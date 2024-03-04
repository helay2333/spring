package com.green.spring.context.annotation;

import java.lang.annotation.*;

/**
 * @author Green写代码
 * @date 2024-03-04 02:21
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
    String value() default "singleton";
}

