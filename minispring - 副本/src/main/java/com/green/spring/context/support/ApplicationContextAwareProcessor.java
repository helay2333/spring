package com.green.spring.context.support;

import com.green.spring.beans.factory.config.BeanPostProcessor;
import com.green.spring.beans.factory.exception.BeanException;
import com.green.spring.context.ApplicationContext;
import com.green.spring.context.ApplicationContextAware;
import lombok.AllArgsConstructor;

/**
 * @author Green写代码
 * @date 2024-02-03 22:09
 */
@AllArgsConstructor
public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private ApplicationContext applicationContext;



    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;

    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }
}
