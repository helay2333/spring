package com.green.spring.beans.factory;

import com.green.spring.beans.factory.exception.BeanException;

/**
 * @author Green写代码
 * @date 2024-02-03 14:07
 */
public interface BeanFactory {
    Object getBean(String beanName, Object... args) throws BeanException;
    <T> T getBean(String beanName, Class<T> requiredType) throws BeanException;
    <T> T getBean(Class<T> requiredType) throws BeanException;
}
