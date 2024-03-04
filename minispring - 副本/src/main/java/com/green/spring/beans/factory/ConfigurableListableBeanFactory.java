package com.green.spring.beans.factory;

import com.green.spring.beans.factory.config.AutowireCapableBeanFactory;
import com.green.spring.beans.factory.config.BeanDefinition;
import com.green.spring.beans.factory.config.ConfigurableBeanFactory;
import com.green.spring.beans.factory.exception.BeanException;

/**
 * @author Green写代码
 * @date 2024-02-03 19:51
 * 处理配置资源加载
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    /**
     * 获取BeanDefinition
     *
     * @param beanName bean名称
     * @return beanDefinition
     * @throws BeanException e
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeanException;

    /**
     * 提前实例化单例bean对象
     *
     * @throws BeanException
     */
    void preInstantiateSingletons() throws BeanException;

}
