package com.green.spring.aop;

/**
 * @author Green写代码
 * @date 2024-02-04 01:13
 * 被代理的对象
 */
public class TargetSource {
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    public Class<?>[] getTargetClass() {
        return target.getClass().getInterfaces();
    }

    public Object getTarget() {
        return target;
    }
}
