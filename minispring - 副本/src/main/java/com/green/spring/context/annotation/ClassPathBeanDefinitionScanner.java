package com.green.spring.context.annotation;


import cn.hutool.core.util.StrUtil;
import com.green.spring.beans.factory.PropertyPlaceholderConfigurer;
import com.green.spring.beans.factory.config.BeanDefinition;
import com.green.spring.beans.factory.config.PropertyValue;
import com.green.spring.beans.factory.config.PropertyValues;
import com.green.spring.beans.factory.support.BeanDefinitionRegistry;
import lombok.AllArgsConstructor;

import java.util.Set;

/**
 * @author Green写代码
 * @date 2024-02-04 02:24
 */



@AllArgsConstructor
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private BeanDefinitionRegistry registry;

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidates) {
                String scope = resolveBeanScope(beanDefinition);
                if (StrUtil.isNotEmpty(scope)) {
                    beanDefinition.setScope(scope);
                }
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }
        registerInnerBean();
    }
    /**
     * 注册Spring内部使用的bean
     */
    private void registerInnerBean() {
        //注册处理注解的BeanPostProcessor
        registry.registerBeanDefinition("autowiredAnnotationBeanPostProcessor"
                , new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
        //注册处理属性占位符的bean
        BeanDefinition propertyPlaceholder = new BeanDefinition(PropertyPlaceholderConfigurer.class);
        PropertyValues propertyPlaceholderPv = new PropertyValues();
        propertyPlaceholderPv.addPropertyValue(new PropertyValue("location", "classpath:application.yml"));
        propertyPlaceholder.setPropertyValues(propertyPlaceholderPv);
        registry.registerBeanDefinition("propertyPlaceholderConfigurer", propertyPlaceholder);
    }

    /**
     * 处理bean的作用域
     * @param beanDefinition
     * @return
     */
    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (null != scope) {
            return scope.value();
        }
        return StrUtil.EMPTY;
    }

    /**
     * 处理bean名称，以Component配置为准，如果没有配置，则取首字母小写的类名
     * @param beanDefinition
     * @return
     */
    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }
}

