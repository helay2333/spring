package com.green.spring.beans.factory.config;

import com.green.spring.beans.factory.exception.BeanException;
import com.green.spring.beans.factory.support.BeanDefinitionRegistry;

/**
 * @author Green写代码
 * @date 2024-02-04 00:11
 */
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {
    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeanException;
}
