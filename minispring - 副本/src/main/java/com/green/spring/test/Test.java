package com.green.spring.test;

import com.green.spring.beans.factory.config.BeanDefinition;
import com.green.spring.beans.factory.config.BeanReference;
import com.green.spring.beans.factory.config.PropertyValue;
import com.green.spring.beans.factory.config.PropertyValues;
import com.green.spring.beans.factory.exception.BeanException;
import com.green.spring.beans.factory.support.BeanDefinitionReader;
import com.green.spring.beans.factory.support.CglibSubclassingInstantiationStrategy;
import com.green.spring.beans.factory.support.DefaultListableBeanFactory;
import com.green.spring.beans.factory.support.SimpleInstantiationStrategy;
import com.green.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.green.spring.core.io.DefaultResourceLoader;
import com.green.spring.core.io.Resource;
import com.green.spring.core.io.ResourceLoader;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Green写代码
 * @date 2024-02-03 14:30
 */
public class Test {
    public static void main1(String[] args) throws InstantiationException, IllegalAccessException {
        //初始化BeanFactory(普通方式)
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory(SimpleInstantiationStrategy.class);
        //注入bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        //获取bean
        UserService userService = (UserService) beanFactory.getBean("userService", "akitsuki");
//        userService.queryUserInfo();
        //获取bean（传入其他参数，这时应该从缓存中获取，参数无效）
        userService = (UserService) beanFactory.getBean("userService", "kouzou");
        //调用bean
//        userService.queryUserInfo();
        //注入其他bean
        beanFactory.registerBeanDefinition("userService2", beanDefinition);
        //获取bean(无参，这里应该会是default)
        userService = (UserService) beanFactory.getBean("userService2");
        //调用bean
//        userService.queryUserInfo();
//        //初始化BeanFactory(cglib方式)
//        beanFactory = new DefaultListableBeanFactory(CglibSubclassingInstantiationStrategy.class);
//        //注入bean
//        beanFactory.registerBeanDefinition("userService", beanDefinition);
//        //获取bean
//        userService = (UserService) beanFactory.getBean("userService", "kouzou");
//        //调用bean
//        userService.queryUserInfo();

    }

    public static void main2(String[] args) throws InstantiationException, IllegalAccessException {
        //初始化bean工厂
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory(SimpleInstantiationStrategy.class);
        //初始化Bean UseDao
        BeanDefinition beanDefinition = new BeanDefinition(UserDao.class);
        beanFactory.registerBeanDefinition("userDao", beanDefinition);
        //初始化Bean UserService
        PropertyValue dummyString = new PropertyValue("dummyString", "dummy");
        PropertyValue dummyInt = new PropertyValue("dummyInt", 114514);
        PropertyValue userDao = new PropertyValue("userDao", new BeanReference("userDao"));
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(dummyInt);
        propertyValues.addPropertyValue(dummyString);
        propertyValues.addPropertyValue(userDao);
        BeanDefinition beanDefinition1 = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition1);
        //获取UserService并使用
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo(1L);
        System.out.println("---------");
        userService.queryUserInfo(5L);
        System.out.println("---------");
        userService.queryUserInfo(114514L);

    }
    private ResourceLoader resourceLoader;

    @Before
    public void init() {
        resourceLoader = new DefaultResourceLoader();
    }

    /**
     * 配置文件读取测试
     */
    public void configReadTest() throws IOException {
        //读取classpath下的资源
        Resource resource = resourceLoader.getResource("classpath:config.yml");
        InputStream is = resource.getInputStream();
        byte[] fileBytes = new byte[is.available()];
        is.read(fileBytes);
        System.out.println(new String(fileBytes));

        //读取指定路径下的资源
        resource = resourceLoader.getResource("src/test/resources/config.yml");
        is = resource.getInputStream();
        fileBytes = new byte[is.available()];
        is.read(fileBytes);
        System.out.println(new String(fileBytes));

        //读取url资源
        resource = resourceLoader.getResource("https://www.baidu.com");
        is = resource.getInputStream();
        fileBytes = new byte[is.available()];
        is.read(fileBytes);
        System.out.println(new String(fileBytes));
    }

    public void testXml() throws InstantiationException, IllegalAccessException, BeanException {
        //初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory(CglibSubclassingInstantiationStrategy.class);
        //读取配置文件，注册bean
        BeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");
        //获取Bean，测试
        UserService userService = (UserService) beanFactory.getBean("userService", UserService.class);
        userService.queryUserInfo(1L);
        userService.queryUserInfo(3L);
        userService.queryUserInfo(5L);
        userService.queryUserInfo(114514L);
    }


}
