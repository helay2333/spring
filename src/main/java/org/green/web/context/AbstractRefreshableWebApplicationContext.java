package org.green.web.context;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;


import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @author Green写代码
 * @date 2024-02-14 02:04
 */
public abstract class AbstractRefreshableWebApplicationContext extends AbstractRefreshableConfigApplicationContext implements ConfigurableWebApplicationContext{
    protected ServletConfig servletConfig;

    protected ServletContext servletContext;
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void setServletConfig(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

    /**
     * 添加后置处理器
     * @param beanFactory
     */
    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new ServletBeanPostProcess(this.servletContext,this.servletConfig));
        // 忽略
        beanFactory.ignoreDependencyInterface(ServletContextAware.class);
        beanFactory.ignoreDependencyInterface(ServletConfigAware.class);
    }


}
