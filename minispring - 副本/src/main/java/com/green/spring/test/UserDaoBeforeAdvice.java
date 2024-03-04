package com.green.spring.test;

import com.green.spring.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author Green写代码
 * @date 2024-02-04 02:12
 */
public class UserDaoBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("方法执行了：" + method);
    }
}