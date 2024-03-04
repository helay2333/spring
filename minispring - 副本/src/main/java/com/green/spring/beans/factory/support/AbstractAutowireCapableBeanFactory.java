package com.green.spring.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.green.spring.beans.DisposableBean;
import com.green.spring.beans.factory.*;
import com.green.spring.beans.factory.config.*;
import com.green.spring.beans.factory.exception.BeanException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;



/**
 * @author Green写代码
 * @date 2024-02-03 14:08
 * 单一职责模式, 此类只是用来满足创建Bean这个功能, 对于获取Bean来说, 是使用其他类实现的
 */

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    /**
     * 实例化策略，在构造方法中进行初始化
     */
    private final InstantiationStrategy strategy;

    protected AbstractAutowireCapableBeanFactory(Class<? extends InstantiationStrategy> clazz) {
        try {
            strategy = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeanException("设置实例化策略出错", e);
        }
    }

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeanException {
        Object bean;
        try {
            bean = resolveBeforeInstantiation(beanName, beanDefinition);
            if (null != bean) {
                return bean;
            }
            bean = createBeanInstance(beanDefinition, beanName, args);
            //设置bean属性之前，允许beanPostProcessor修改属性值
            applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);
            //设置bean属性
            applyPropertyValues(beanName, beanDefinition, bean);
            //初始化bean，执行beanPostProcessor的前置和后置方法
            initializeBean(beanName, bean, beanDefinition);
            //注册实现了DisposableBean接口的对象
            registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
            //创建好的单例bean，放入缓存
            if(beanDefinition.isSingleton()){
                addSingleton(beanName, bean);
            }
        } catch (Exception e) {
            throw new BeanException("创建bean失败", e);
        }
        return bean;
    }
    protected void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        for(BeanPostProcessor processor : getBeanPostProcessors()) {
            if (processor instanceof InstantiationAwareBeanPostProcessor) {
                PropertyValues pvs = ((InstantiationAwareBeanPostProcessor) processor).postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                if (null != pvs) {
                    for (PropertyValue pv : pvs.getPropertyValues()) {
                        beanDefinition.getPropertyValues().addPropertyValue(pv);
                    }
                }
            }
        }
    }

    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (null != bean) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }
    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            if (processor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) processor).postProcessBeforeInstantiation(beanClass, beanName);
                if (null != result) {
                    return result;
                }
            }
        }
        return null;
    }
    /**
     * 在需要的情况下，注册销毁方法
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        //只有单例Bean才需要销毁
        if(!beanDefinition.isSingleton()){
            return ;
        }
        if (bean instanceof DisposableBean || StrUtil.isNotBlank(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) throws BeanException {
        //拿到所有的构造方法
        Constructor<?>[] declaredConstructors = beanDefinition.getBeanClass().getDeclaredConstructors();
        //开始循环，找到和参数类型一致的方法
        for (Constructor<?> c : declaredConstructors) {
            if (c.getParameterTypes().length == args.length) {
                boolean flag = true;
                for (int i = 0; i < c.getParameterTypes().length; i++) {
                    if (!args[i].getClass().equals(c.getParameterTypes()[i])) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    //调用策略来进行实例化
                    return strategy.instantiate(beanDefinition, beanName, c, args);
                }
            }
        }
        throw new BeanException("创建bean出错，未找到对应构造方法");
    }

    /**
     * 为生成的bean设置属性
     *
     * @param beanName       bean的名称
     * @param beanDefinition bean的定义
     * @param bean           等待设置属性的bean
     */
    protected void applyPropertyValues(String beanName, BeanDefinition beanDefinition, Object bean) throws BeanException {
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue pv : propertyValues.getPropertyValues()) {
            String name = pv.getName();
            Object value = pv.getValue();
            if (value instanceof BeanReference) {
                try {
                    value = getBean(((BeanReference) value).getName());
                } catch (Exception e) {
                    throw new BeanException("设置bean属性时异常:" + beanName, e);
                }
            }
            BeanUtil.setFieldValue(bean, name, value);
        }
    }

    /**
     * 初始化bean
     *
     * @param beanName       bean名称
     * @param bean           待初始化bean
     * @param beanDefinition bean定义
     * @return bean
     */
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        //执行Aware感知操作
        invokeAwareMethods(beanName, bean);
        //执行beanPostProcessor before处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
        //执行bean初始化内容
        try {
            invokeInitMethod(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeanException("执行bean初始化方法失败，bean名称：" + beanName);
        }
        //执行beanPostProcessor after处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        return wrappedBean;

    }
    private void invokeAwareMethods(String beanName, Object bean) {
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getClassLoader());
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }
    }


    private void invokeInitMethod(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        //实现了接口InitializingBean的bean，调用接口方法
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        //配置了initMethod的bean，调用初始化方法
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotBlank(initMethodName)) {
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            initMethod.invoke(bean);
        }
    }


    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeanException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(existingBean, beanName);
            if (null == current) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeanException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(existingBean, beanName);
            if (null == current) {
                return result;
            }
            result = current;
        }
        return result;
    }
}

