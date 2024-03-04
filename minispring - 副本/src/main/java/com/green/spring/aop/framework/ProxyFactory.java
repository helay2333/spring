package com.green.spring.aop.framework;

import com.green.spring.aop.AdvisedSupport;
import lombok.AllArgsConstructor;

/**
 * @author Green写代码
 * @date 2024-02-04 01:49
 */

@AllArgsConstructor
public class ProxyFactory {

    private AdvisedSupport advisedSupport;

    public Object getProxy() {
        return createProxy().getProxy();
    }

    private AopProxy createProxy() {
        if (advisedSupport.isProxyTargetClass()) {
            return new Cglib2AopProxy(advisedSupport);
        }
        return new JdkDynamicAopProxy(advisedSupport);
    }
}


