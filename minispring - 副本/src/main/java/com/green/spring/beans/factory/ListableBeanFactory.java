package com.green.spring.beans.factory;

import com.green.spring.beans.factory.exception.BeanException;

import java.util.Map;

/**
 * @author Green写代码
 * @date 2024-02-03 19:51
 */
public interface ListableBeanFactory extends BeanFactory {
    /**
     * 按照类型返回 Bean 实例
     * @param type bean type
     * @param <T>  type
     * @return bean
     * @throws BeanException e
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException;

    /**
     * 返回注册表中所有的Bean名称
     * @return bean name list
     */
    String[] getBeanDefinitionNames();
}