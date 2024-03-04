package com.green.spring.beans.factory.support;

import com.green.spring.beans.factory.config.BeanDefinition;
import com.green.spring.beans.factory.exception.BeanException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Green写代码
 * @date 2024-02-03 14:38
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy{
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor<?> constructor, Object[] args) throws BeanException {
        Class<?>clazz = beanDefinition.getBeanClass();
        try{
            if(null != constructor){
                return constructor.newInstance(args);
            }else{
                return clazz.getDeclaredConstructor().newInstance();
            }
        }catch (InvocationTargetException e) {
            throw new BeanException("实例化Bean失败");
        } catch (InstantiationException e) {
            throw new BeanException("实例化Bean失败");
        } catch (IllegalAccessException e) {
            throw new BeanException("实例化Bean失败");
        } catch (NoSuchMethodException e) {
            throw new BeanException("实例化Bean失败");
        }
    }
}
