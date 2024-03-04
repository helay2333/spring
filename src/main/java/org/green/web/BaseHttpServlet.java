package org.green.web;

import org.green.web.context.AbstractRefreshableWebApplicationContext;
import org.green.web.context.WebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * @author Green写代码
 * @date 2024-02-14 23:48
 */
public abstract class BaseHttpServlet extends HttpServlet {
    protected ApplicationContext webApplicationContext;
    public BaseHttpServlet(ApplicationContext webApplicationContext){
        this.webApplicationContext = webApplicationContext;
    }

    /**
     * 初始化IOC
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        final ServletContext servletContext = getServletContext();

        ApplicationContext rootContext = (ApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_NAME);

        if (!ObjectUtils.isEmpty(webApplicationContext)){

            if (!(this.webApplicationContext instanceof AnnotationConfigApplicationContext)){
                // 需要转换
                AbstractRefreshableWebApplicationContext wac = (AbstractRefreshableWebApplicationContext) this.webApplicationContext;
                // 设置父子容器
                if (wac.getParent() == null){
                    wac.setParent(rootContext);
                }
                // 配置上下文
                wac.setServletContext(servletContext);
                wac.setServletConfig(getServletConfig());
                // web容器刷新
                wac.refresh();
            }
        }
        onRefresh(webApplicationContext);
    }

    protected abstract void onRefresh(ApplicationContext webApplicationContext);
}
