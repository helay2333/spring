package com.green.spring.beans.factory.support;

import com.green.spring.beans.factory.ConfigurableListableBeanFactory;
import com.green.spring.beans.factory.config.BeanDefinition;
import com.green.spring.beans.factory.exception.BeanException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Green写代码
 * @date 2024-02-03 14:09
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    public DefaultListableBeanFactory() {
        this(CglibSubclassingInstantiationStrategy.class);
    }

    public DefaultListableBeanFactory(Class<? extends InstantiationStrategy> clazz) {
        super(clazz);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeanException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (null == beanDefinition) {
            throw new BeanException("没有名称为" + beanName + "的Bean定义");
        }
        return beanDefinition;
    }

    @Override
    public void preInstantiateSingletons() throws BeanException {
        beanDefinitionMap.keySet().forEach(this::getBean);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return beanDefinitionMap.containsKey(name);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
        Map<String, T> resultMap = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            if (type.isAssignableFrom(beanDefinition.getBeanClass())) {
                resultMap.put(beanName, getBean(beanName, type));
            }
        });
        return resultMap;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeanException {
        List<Map.Entry<String, BeanDefinition>> filteredDefinition = beanDefinitionMap.entrySet().stream()
                .filter(x -> requiredType.isAssignableFrom(x.getValue().getBeanClass()))
                .collect(Collectors.toList());
        if (1 == filteredDefinition.size()) {
            return getBean(filteredDefinition.get(0).getKey(), requiredType);
        }
        throw new BeanException(requiredType + "expect 1 single bean but found " + filteredDefinition.size() + " "
                + filteredDefinition.stream().map(Map.Entry::getKey).collect(Collectors.toList()));
    }

}
