package com.green.spring.beans.factory;

import com.green.spring.beans.factory.exception.BeanException;

/**
 * @author Green写代码
 * @date 2024-02-03 22:06
 */
public interface BeanFactoryAware extends Aware {
    /**
     * 设置所属的BeanFactory
     * @param beanFactory
     * @throws BeanException
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeanException;
}

