package org.green.web.test.service;

import org.green.web.spring.BeanPostProcessor;
import org.green.web.spring.Component;
import org.springframework.beans.BeansException;

/**
 * @author Green写代码
 * @date 2024-02-13 16:08
 */
@Component("greenBeanPostProcessor")
public class GreenBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("调用了GreenBeanPostProcessor" + "对bean和beanName做一些增强行为" );
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
}
