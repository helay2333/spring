package com.green.spring.context.support;

import com.green.spring.beans.factory.support.DefaultListableBeanFactory;
import com.green.spring.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author Green写代码
 * @date 2024-02-03 19:53
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (null != configLocations) {
            reader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * 获取配置文件的路径
     *
     * @return
     */
    protected abstract String[] getConfigLocations();
}
