package com.green.spring.beans.factory.support;

import com.green.spring.beans.factory.BeanFactory;
import com.green.spring.beans.factory.FactoryBean;
import com.green.spring.beans.factory.config.BeanDefinition;
import com.green.spring.beans.factory.config.BeanPostProcessor;
import com.green.spring.beans.factory.config.ConfigurableBeanFactory;
import com.green.spring.beans.factory.config.DefaultSingletonBeanRegistry;
import com.green.spring.beans.factory.exception.BeanException;
import com.green.spring.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Green写代码
 * @date 2024-02-03 14:09
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    @Override
    public Object getBean(String beanName, Object... args) throws BeanException {
        Object bean = getSingleton(beanName);
        if (null != bean) {
            return getObjectForBeanInstance(bean, beanName);
        }
        //获取单例Bean失败的情况，需要先调用抽象的获取bean定义的方法，拿到bean的定义，然后再通过这个来新创建一个Bean
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        bean = createBean(beanName, beanDefinition, args);
        return getObjectForBeanInstance(bean, beanName);
    }
    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (beanInstance instanceof FactoryBean) {
            return getObjectFromFactoryBean(((FactoryBean<?>) beanInstance), beanName);
        }
        return beanInstance;
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeanException {
        Object bean = getBean(beanName);
        return requiredType.cast(bean);
    }
    /**
     * 获取BeanDefinition
     * @param beanName
     * @return
     * @throws BeanException
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeanException;

    /**
     * 通过BeanDefinition创建一个Bean
     * @param beanName
     * @param beanDefinition
     * @return
     * @throws BeanException
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeanException;
    @Override
    public void addBeanPostProcessor(BeanPostProcessor processor) {
        this.beanPostProcessors.remove(processor);
        this.beanPostProcessors.add(processor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }
    private ClassLoader classLoader = ClassUtils.getDefaultClassLoader();


    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

}
