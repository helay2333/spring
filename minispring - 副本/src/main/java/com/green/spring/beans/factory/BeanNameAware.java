package com.green.spring.beans.factory;

/**
 * @author Green写代码
 * @date 2024-02-03 22:06
 */
public interface BeanNameAware extends Aware{

    void setBeanName(String beanName);
}
