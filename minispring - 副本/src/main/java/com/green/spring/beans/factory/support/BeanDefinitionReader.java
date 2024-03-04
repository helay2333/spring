package com.green.spring.beans.factory.support;

import com.green.spring.beans.factory.exception.BeanException;
import com.green.spring.core.io.Resource;
import com.green.spring.core.io.ResourceLoader;

/**
 * @author Green写代码
 * @date 2024-02-03 17:41
 */
public interface BeanDefinitionReader {
    /**
     * 获取bean定义的registry
     * @return registry
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 获取resource loader
     * @return resource loader
     */
    ResourceLoader getResourceLoader();
    /**
     * 读取bean定义(通过resource)
     *
     * @param resource res
     * @throws BeanException e
     */
    void loadBeanDefinitions(Resource resource) throws BeanException;

    /**
     * 读取bean定义(通过多个resource)
     *
     * @param resources res
     * @throws BeanException e
     */
    void loadBeanDefinitions(Resource... resources) throws BeanException;

    /**
     * 读取bean定义(通过路径)
     *
     * @param location location
     * @throws BeanException e
     */
    void loadBeanDefinitions(String location) throws BeanException;

    /**
     * 读取bean定义(通过多个路径)
     *
     * @param location
     * @throws BeanException
     */
    void loadBeanDefinitions(String... location) throws BeanException;
}
