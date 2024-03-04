package com.green.spring.context.annotation;

import cn.hutool.core.bean.BeanUtil;
import com.green.spring.beans.factory.BeanFactory;
import com.green.spring.beans.factory.BeanFactoryAware;
import com.green.spring.beans.factory.ConfigurableListableBeanFactory;
import com.green.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.green.spring.beans.factory.config.PropertyValues;
import com.green.spring.beans.factory.exception.BeanException;
import com.green.spring.util.ClassUtils;

import java.lang.reflect.Field;

/**
 * @author Green写代码
 * @date 2024-02-04 02:49
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException {
        return null;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeanException {
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            resolveAutowiredAnnotation(bean, field);
        }
        return null;
    }



    /**
     * 处理@Autowired和@Qualifier注解
     * @param bean
     * @param field
     */
    private void resolveAutowiredAnnotation(Object bean, Field field) {
        Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
        if (null != autowiredAnnotation) {
            Class<?> fieldType = field.getType();
            String dependBeanName;
            Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
            Object dependBean;
            if (null != qualifierAnnotation) {
                dependBeanName = qualifierAnnotation.value();
                dependBean = beanFactory.getBean(dependBeanName, fieldType);
            } else {
                dependBean = beanFactory.getBean(fieldType);
            }
            BeanUtil.setFieldValue(bean, field.getName(), dependBean);
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }
}

