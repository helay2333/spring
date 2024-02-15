package org.green.web;

import org.green.web.context.WebApplicationContext;
import org.springframework.context.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * @author Green写代码
 * @date 2024-02-14 23:48
 */
public class BaseHttpServlet extends HttpServlet {
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

    }
}
