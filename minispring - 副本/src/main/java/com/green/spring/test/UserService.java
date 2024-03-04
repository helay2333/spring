package com.green.spring.test;

import com.green.spring.beans.DisposableBean;
import com.green.spring.beans.factory.*;
import com.green.spring.context.ApplicationContext;
import com.green.spring.context.ApplicationContextAware;
import lombok.Data;

/**
 * @author Green写代码
 * @date 2024-02-03 14:30
 */

@Data
public class UserService implements InitializingBean, DisposableBean,
        BeanFactoryAware, BeanClassLoaderAware, BeanNameAware, ApplicationContextAware {

    private BeanFactory beanFactory;

    private ClassLoader beanClassLoader;

    private String beanName;

    private ApplicationContext applicationContext;

    private String dummyString;

    private int dummyInt;

    private UserDao userDao;

    public void queryUserInfo(Long id) {
        System.out.println("dummyString:" + dummyString);
        System.out.println("dummyInt:" + dummyInt);
        String userName = userDao.queryUserName(id);
        if (null == userName) {
            System.out.println("用户未找到>_<");
        } else {
            System.out.println("用户名:" + userDao.queryUserName(id));
        }
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("userService的destroy执行了");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("userService的afterPropertiesSet执行了");
    }
}


