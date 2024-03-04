package com.green.spring.beans.factory.config;

import com.green.spring.beans.factory.exception.BeanException;

/**
 * @author Green写代码
 * @date 2024-02-03 19:52
 */
public interface BeanPostProcessor {
    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     *
     * @param bean     准备初始化的bean
     * @param beanName bean的名称
     * @return 修改后的bean
     * @throws BeanException e
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException;

    /**
     * 在 Bean 对象执行初始化方法之后，执行此方法
     *
     * @param bean     初始化后的bean
     * @param beanName bean的名称
     * @return 修改后的bean
     * @throws BeanException e
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException;
}
