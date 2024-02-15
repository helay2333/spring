package org.green.web;

import org.green.web.context.WebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.Filter;
import javax.servlet.ServletContext;

/**
 * @author Green写代码
 * @date 2024-02-14 23:56
 */
public abstract class AbstractDispatcherServletInitializer implements WebApplicationInitializer{


    @Override
    public void onStartUp(ServletContext servletContext) {
        //创建父容器
        final ApplicationContext rootApplicationContext =createRootApplicationContext();

    }


    // 过滤器
    protected abstract Filter[] getFilters();

    // 映射器
    protected String[] getMappings(){
        return new String[]{"/"};
    }

    // 创建父容器
    protected abstract AnnotationConfigApplicationContext createRootApplicationContext();

    // 创建子容器
    protected abstract WebApplicationContext createWebApplicationContext();

    // 获取包扫描配置类
    protected abstract Class<?>[] getRootConfigClasses();

    protected abstract Class<?>[] getWebConfigClasses();
}

