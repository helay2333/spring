package com.green.spring.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.green.spring.beans.DisposableBean;
import com.green.spring.beans.factory.config.BeanDefinition;
import lombok.AllArgsConstructor;

/**
 * @author Green写代码
 * @date 2024-03-03 21:30
 */


import java.lang.reflect.Method;


@AllArgsConstructor
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private final BeanDefinition beanDefinition;

    @Override
    public void destroy() throws Exception {
        //实现了DisposableBean接口
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        //配置了destroy-method
        if (StrUtil.isNotBlank(beanDefinition.getDestroyMethodName()) &&
                !(bean instanceof DisposableBean && "destroy".equals(beanDefinition.getDestroyMethodName()))) {
            Method destroyMethod = beanDefinition.getBeanClass().getMethod(beanDefinition.getDestroyMethodName());
            destroyMethod.invoke(bean);
        }
    }
}

