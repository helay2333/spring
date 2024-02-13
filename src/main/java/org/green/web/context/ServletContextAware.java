package org.green.web.context;

import org.springframework.beans.factory.Aware;

import javax.servlet.ServletContext;

/**
 * @author Green写代码
 * @date 2024-02-14 02:09
 */
public class ServletContextAware implements Aware {
    public void setServletContext(ServletContext servletContext) {
    }


}
