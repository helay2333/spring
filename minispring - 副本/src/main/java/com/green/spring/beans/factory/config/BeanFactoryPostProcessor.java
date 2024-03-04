package com.green.spring.beans.factory.config;

import com.green.spring.beans.factory.ConfigurableListableBeanFactory;

/**
 * @author Green写代码
 * @date 2024-02-03 19:52
 */
public interface BeanFactoryPostProcessor {
    /**
     * 在所有的BeanDefinition加载完成后，实例化Bean对象前，提供修改BeanDefinition的机制
     * @param beanFactory
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory);
}
