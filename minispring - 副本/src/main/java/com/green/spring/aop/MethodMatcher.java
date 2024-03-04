package com.green.spring.aop;

import java.lang.reflect.Method;

/**
 * @author Green写代码
 * @date 2024-02-04 01:13
 */
public interface MethodMatcher {
    /**
     * 是否匹配
     * @param method 要匹配的方法
     * @param clazz  要匹配的类
     * @return result
     */
    boolean matches(Method method, Class<?> clazz);
}
