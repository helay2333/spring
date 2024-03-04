package com.green.spring.beans.factory.support;

import com.green.spring.beans.factory.config.BeanDefinition;
import com.green.spring.beans.factory.exception.BeanException;

/**
 * @author Green写代码
 * @date 2024-02-03 14:09
 */
public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String beanName) throws BeanException;
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
    boolean containsBeanDefinition(String name);
}
