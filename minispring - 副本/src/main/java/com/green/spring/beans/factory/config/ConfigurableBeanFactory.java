package com.green.spring.beans.factory.config;

import com.green.spring.beans.factory.HierarchicalBeanFactory;

/**
 * @author Green写代码
 * @date 2024-02-03 19:52
 */

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加一个processor
     *
     * @param processor processor
     */
    void addBeanPostProcessor(BeanPostProcessor processor);
    /**
     * 销毁单例对象
     */
    void destroySingletons();
}

