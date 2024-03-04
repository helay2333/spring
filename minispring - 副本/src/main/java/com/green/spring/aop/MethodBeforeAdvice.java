package com.green.spring.aop;

import java.lang.reflect.Method;

/**
 * @author Green写代码
 * @date 2024-02-04 01:36
 */
public interface MethodBeforeAdvice extends BeforeAdvice{
    void before(Method method, Object[] args, Object target) throws Throwable;
}
