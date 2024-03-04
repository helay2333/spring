package com.green.spring.beans.factory;

/**
 * @author Green写代码
 * @date 2024-02-03 23:55
 */

public interface FactoryBean<T> {
    /**
     * 获取对象
     * @return 对象
     * @throws Exception e
     */
    T getObject() throws Exception;

    /**
     * 获取对象类型
     * @return 对象类型class
     */
    Class<?> getObjectType();

    /**
     * 是否为单例
     * @return
     */
    boolean isSingleton();
}
