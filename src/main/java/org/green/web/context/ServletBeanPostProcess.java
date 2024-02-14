package org.green.web.context;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @author Green写代码
 * @date 2024-02-14 02:07
 */
public class ServletBeanPostProcess implements BeanPostProcessor {
    private ServletContext servletContext;
    private ServletConfig servletConfig;
    public ServletBeanPostProcess(ServletContext servletContext, ServletConfig servletConfig){
        this.servletContext = servletContext;
        this.servletConfig = servletConfig;
    }

    /**
     * 用户拿到ServletContext/ServletConfig
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean != null && bean instanceof ServletContextAware){
            ((ServletContextAware)bean).setServletContext(this.servletContext);
        }
        if(bean != null && bean instanceof ServletConfigAware){
            ((ServletConfigAware)bean).setServletConfig(this.servletConfig);
        }
        return bean;
    }
}
