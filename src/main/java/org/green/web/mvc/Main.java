package org.green.web.mvc;

import org.green.web.context.AnnotationConfigWebApplicationContext;
import org.green.web.test.service.UserService;

import java.util.HashMap;

/**
 * @author Green写代码
 * @date 2024-02-14 02:23
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);
        context.refresh();
        System.out.println(context.getBean(Service.class));
//        HashMap
    }
}
