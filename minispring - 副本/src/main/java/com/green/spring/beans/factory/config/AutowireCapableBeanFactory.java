package com.green.spring.beans.factory.config;

import com.green.spring.beans.factory.BeanFactory;
import com.green.spring.beans.factory.exception.BeanException;

/**
 * @author Green写代码
 * @date 2024-02-03 19:52
 */

public interface AutowireCapableBeanFactory extends BeanFactory {


    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessBeforeInitialization 方法
     *
     * @param existingBean bean
     * @param beanName     bean名称
     * @return bean
     * @throws BeanException e
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeanException;

    /**
     * 执行 BeanPostProcessors 接口实现类的 postProcessorsAfterInitialization 方法
     *
     * @param existingBean bean
     * @param beanName     bean名称
     * @return bean
     * @throws BeanException e
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeanException;
}

