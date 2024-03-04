package com.green.spring.beans.factory;

import com.green.spring.beans.factory.config.BeanDefinition;
import com.green.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.green.spring.beans.factory.config.PropertyValue;
import com.green.spring.beans.factory.config.PropertyValues;
import com.green.spring.beans.factory.exception.BeanException;
import com.green.spring.core.io.DefaultResourceLoader;
import com.green.spring.core.io.Resource;

import java.io.IOException;
import java.util.Properties;
/**
 * @author Green写代码
 * @date 2024-02-04 02:19
 */



public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for (String beanName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for(PropertyValue pv : propertyValues.getPropertyValues()) {
                    Object value = pv.getValue();
                    if (!(value instanceof String)) {
                        continue;
                    }
                    String strValue = (String) value;
                    StringBuilder sb = new StringBuilder(strValue);
                    int startIndex = strValue.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                    int endIndex = strValue.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                    if (-1 != startIndex && -1 != endIndex && startIndex < endIndex) {
                        String propKey = strValue.substring(startIndex + 2, endIndex);
                        String propValue = properties.getProperty(propKey);
                        sb.replace(startIndex, endIndex + 1, propValue);
                        propertyValues.addPropertyValue(new PropertyValue(pv.getName(), sb.toString()));
                    }
                }
            }

        } catch (IOException e) {
            throw new BeanException("加载配置时出错", e);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

