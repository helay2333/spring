package org.green.web.context;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @author zzzy0
 */
public interface ConfigurableWebApplicationContext extends WebApplicationContext{
    void setServletContext(ServletContext servletContext);
    void setServletConfig(ServletConfig servletConfig);
    ServletContext getServletContext();
    ServletConfig getServletConfig();
}
