package com.green.spring.test;

import com.green.spring.beans.factory.config.BeanPostProcessor;
import com.green.spring.beans.factory.exception.BeanException;

/**
 * @author Green写代码
 * @date 2024-02-03 20:48
*/
public class MyBeanPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setDummyInt(1919810);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }
}

