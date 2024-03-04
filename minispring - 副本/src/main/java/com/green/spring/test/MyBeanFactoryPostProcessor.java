package com.green.spring.test;

import com.green.spring.beans.factory.ConfigurableListableBeanFactory;
import com.green.spring.beans.factory.config.BeanDefinition;
import com.green.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.green.spring.beans.factory.config.PropertyValue;
import com.green.spring.beans.factory.config.PropertyValues;

/**
 * @author Green写代码
 * @date 2024-02-03 20:46
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("dummyString", "翻斗花园一棵树，我叫英俊你记住"));
    }
}
