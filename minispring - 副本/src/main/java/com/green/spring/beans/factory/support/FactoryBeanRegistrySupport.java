package com.green.spring.beans.factory.support;

import com.green.spring.beans.factory.FactoryBean;
import com.green.spring.beans.factory.config.DefaultSingletonBeanRegistry;
import com.green.spring.beans.factory.exception.BeanException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Green写代码
 * @date 2024-02-03 23:55
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();
    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object obj = factoryBeanObjectCache.get(beanName);
        return obj == NULL_OBJECT ? null : obj;// 防止NULL放入factoryBeanObjectCache, 因为CocurrentHashMap不允许存放NULL
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) {
        if (factoryBean.isSingleton()) {
            Object obj = getCachedObjectForFactoryBean(beanName);
            if (null == obj) {
                obj = doGetObjectFromFactoryBean(factoryBean, beanName);
                factoryBeanObjectCache.put(beanName, null == obj ? NULL_OBJECT: obj);
            }
            return obj == NULL_OBJECT ? null : obj;
        } else {
            return doGetObjectFromFactoryBean(factoryBean, beanName);
        }
    }

    private Object doGetObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) {
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new BeanException("factory bean threw exception on object [" + beanName + "] creation.");
        }
    }

}
