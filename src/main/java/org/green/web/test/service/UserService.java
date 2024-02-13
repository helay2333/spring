package org.green.web.test.service;

import org.green.web.spring.Autowired;
import org.green.web.spring.Component;
import org.green.web.spring.Scope;

/**
 * @author Green写代码
 * @date 2024-02-13 14:22
 */
@Scope("singleton")
@Component("userService")
public class UserService {
    @Autowired
    private OrderService orderService;
    public void test(){
        System.out.println(orderService);
    }
}
