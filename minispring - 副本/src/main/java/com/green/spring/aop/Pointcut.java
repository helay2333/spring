package com.green.spring.aop;

/**
 * @author Green写代码
 * @date 2024-02-04 01:13
 */
public interface Pointcut {
    ClassFilter getClassFilter();

    /**
     * 获取method匹配器
     * @return
     */
    MethodMatcher getMethodMatcher();
}
