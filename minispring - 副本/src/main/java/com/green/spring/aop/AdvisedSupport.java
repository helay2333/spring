package com.green.spring.aop;

import lombok.Data;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Green写代码
 * @date 2024-02-04 01:13
 */
@Data
public class AdvisedSupport {
    /**
     * 被代理的目标对象
     */
    private TargetSource targetSource;

    /**
     * 方法拦截器
     */
    private MethodInterceptor methodInterceptor;

    /**
     * 方法匹配器
     */
    private MethodMatcher methodMatcher;
    /**
     * true    cglib代理
     * false   jdk代理
     */
    private boolean proxyTargetClass;
}