package org.green.web.mvc;


import org.green.web.context.ServletContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;

/**
 * @author Green写代码
 * @date 2024-02-14 02:23
 */
@Component("service")
public class Service implements ServletContextAware {
    @Override
    public void setServletContext(ServletContext servletContext) {
        System.out.println("setServletContext");
    }
}
