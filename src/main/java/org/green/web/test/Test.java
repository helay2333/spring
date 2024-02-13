package org.green.web.test;

import org.green.web.spring.GreenApplicationContext;
import org.green.web.test.service.UserService;

/**
 * @author Green写代码
 * @date 2024-02-13 14:22
 */
public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        GreenApplicationContext context = new GreenApplicationContext(AppConfig.class);
        UserService userService = (UserService) context.getBean("userService");
        UserService userService1 = (UserService) context.getBean("userService");
        System.out.println(userService);
        System.out.println(userService1);
        userService.test();
    }
}
