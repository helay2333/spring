package com.green.spring.beans.factory.support;

import com.green.spring.beans.factory.config.BeanDefinition;
import com.green.spring.beans.factory.exception.BeanException;

import java.lang.reflect.Constructor;

/**
 * @author Green写代码
 * @date 2024-02-03 14:38
 * 这里是策略模式
 */
public interface InstantiationStrategy {

    /**
     * 实例化Bean
     * @param beanDefinition
     * @param beanName
     * @param constructor
     * @param args
     * @return
     * @throws BeanException
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> constructor, Object[] args) throws BeanException;
}
