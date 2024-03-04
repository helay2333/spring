package org.green.web;

import org.green.web.context.AnnotationConfigWebApplicationContext;
import org.green.web.context.WebApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.ObjectUtils;

import javax.servlet.Filter;

/**
 * @author Green写代码
 * @date 2024-02-17 01:19
 */
public abstract class AbstractAnnotationConfigDispatcherServletInitializer extends AbstractDispatcherServletInitializer{

    @Override
    protected Filter[] getFilters() {
        return new Filter[0];
    }

    @Override
    protected AnnotationConfigApplicationContext createRootApplicationContext() {
        final Class<?>[] rootConfigClasses = getRootConfigClasses();
        if (!ObjectUtils.isEmpty(rootConfigClasses)){
            final AnnotationConfigApplicationContext rootContext = new AnnotationConfigApplicationContext();
            rootContext.register(rootConfigClasses);
            return rootContext;
        }
        return null;
    }

    @Override
    protected WebApplicationContext createWebApplicationContext() {
        final Class<?>[] rootConfigClasses = getWebConfigClasses();
        if (!ObjectUtils.isEmpty(rootConfigClasses)){
            final AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
            webContext.register(rootConfigClasses);
            return webContext;
        }
        return null;
    }

}
