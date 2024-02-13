package org.green.web.test.service;

import org.green.web.spring.Component;
import org.green.web.spring.Scope;

/**
 * @author Green写代码
 * @date 2024-02-13 14:22
 */
@Scope("prototype")
@Component("userService")
public class UserService {
    public void test(){
        System.out.println("test");
    }
}
