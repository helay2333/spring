package com.green.spring.context.support;

import com.green.spring.beans.factory.ConfigurableListableBeanFactory;
import com.green.spring.beans.factory.config.BeanDefinitionRegistryPostProcessor;
import com.green.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.green.spring.beans.factory.config.BeanPostProcessor;
import com.green.spring.beans.factory.exception.BeanException;
import com.green.spring.beans.factory.support.BeanDefinitionRegistry;
import com.green.spring.context.ConfigurableApplicationContext;
import com.green.spring.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * @author Green写代码
 * @date 2024-02-03 19:53
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeanException {
        //创建beanFactory，加载beanDefinition
        refreshBeanFactory();
        //获取beanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        //添加ApplicationContextAwareProcessor
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        //在bean实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);
        //注册BeanPostProcessor
        registerBeanPostProcessors(beanFactory);
        //提前实例化单例bean对象
        beanFactory.preInstantiateSingletons();
    }

    /**
     * 刷新beanFactory
     *
     * @throws BeanException
     */
    protected abstract void refreshBeanFactory() throws BeanException;

    /**
     * 获取beanFactory
     *
     * @return
     */
    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    /**
     * 调用beanFactoryPostProcessor
     *
     * @param beanFactory
     * @throws BeanException
     */
    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) throws BeanException {
        for (BeanFactoryPostProcessor processor : beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values()) {
            //如果处理器为bean定义后置处理器，则先处理这个
            if (processor instanceof BeanDefinitionRegistryPostProcessor) {
                ((BeanDefinitionRegistryPostProcessor) processor).postProcessBeanDefinitionRegistry((BeanDefinitionRegistry) beanFactory);
            }
            processor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 注册beanPostProcessor
     *
     * @param beanFactory
     * @throws BeanException
     */
    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) throws BeanException {
        for (BeanPostProcessor processor : beanFactory.getBeansOfType(BeanPostProcessor.class).values()) {
            beanFactory.addBeanPostProcessor(processor);
        }
    }
    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        getBeanFactory().destroySingletons();
    }


    @Override
    public Object getBean(String beanName, Object... args) throws BeanException {
        return getBeanFactory().getBean(beanName, args);
    }


    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws BeanException {
        return getBeanFactory().getBean(beanName, requiredType);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeanException {
        return getBeanFactory().getBean(requiredType);
    }


    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }


}
