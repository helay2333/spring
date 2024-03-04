package com.green.spring.beans.factory.config;

import com.green.spring.beans.DisposableBean;
import com.green.spring.beans.factory.exception.BeanException;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例bean获取
 * @author Green写代码
 * @date 2024-02-03 14:08
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{
    private final Map<String, Object> singletonBeans = new HashMap<>();
    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();
    protected static final Object NULL_OBJECT = new Object();

    @Override
    public Object getSingleton(String beanName) {
        return singletonBeans.get(beanName);
    }

    @Override
    public void addSingleton(String beanName, Object bean) {
        singletonBeans.put(beanName, bean);
    }

    @Override
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }
    /**
     * 销毁单例对象
     */
    public void destroySingletons() {
        String[] keys = disposableBeans.keySet().toArray(new String[0]);
        for (String key: keys) {
            DisposableBean bean = disposableBeans.remove(key);
            try {
                bean.destroy();
            } catch (Exception e) {
                throw new BeanException("exception on destroy bean " + key, e);
            }
        }
    }

}
