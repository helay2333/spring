package com.green.spring.context.support;

import com.green.spring.beans.factory.ConfigurableListableBeanFactory;
import com.green.spring.beans.factory.exception.BeanException;
import com.green.spring.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author Green写代码
 * @date 2024-02-03 19:53
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{
    private DefaultListableBeanFactory beanFactory;
    @Override
    protected void refreshBeanFactory() throws BeanException {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        //读取beanDefinition
        loadBeanDefinitions(defaultListableBeanFactory);
        this.beanFactory = defaultListableBeanFactory;

    }

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);
}
