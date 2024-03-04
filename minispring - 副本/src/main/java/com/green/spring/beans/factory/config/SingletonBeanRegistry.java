package com.green.spring.beans.factory.config;

import com.green.spring.beans.DisposableBean;

/**
 * @author Green写代码
 * @date 2024-02-03 14:08
 */
public interface SingletonBeanRegistry {
    Object getSingleton(String beanName);

    void addSingleton(String beanName,Object bean);

    /**
     * 注册销毁逻辑
     * @param beanName
     * @param bean
     */
    void registerDisposableBean(String beanName, DisposableBean bean);
}
