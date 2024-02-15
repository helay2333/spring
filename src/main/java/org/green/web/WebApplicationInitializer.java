package org.green.web;

import javax.servlet.ServletContext;

/**
 * @author Green写代码
 * @date 2024-02-14 23:53
 */
public interface WebApplicationInitializer {
    void onStartUp(ServletContext servletContext);
}
