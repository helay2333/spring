package org.green.web.context;

import org.springframework.beans.factory.Aware;

import javax.servlet.ServletConfig;

/**
 * @author Green写代码
 * @date 2024-02-14 02:11
 */
public class ServletConfigAware implements Aware {
    public void setServletConfig(ServletConfig servletConfig) {
    }
}
