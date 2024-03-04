import com.green.spring.beans.factory.exception.BeanException;
import com.green.spring.beans.factory.support.BeanDefinitionReader;
import com.green.spring.beans.factory.support.CglibSubclassingInstantiationStrategy;
import com.green.spring.beans.factory.support.DefaultListableBeanFactory;
import com.green.spring.beans.factory.support.SimpleInstantiationStrategy;
import com.green.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.green.spring.context.support.ClasspathXmlApplicationContext;
import com.green.spring.core.io.DefaultResourceLoader;
import com.green.spring.core.io.Resource;
import com.green.spring.core.io.ResourceLoader;
import com.green.spring.test.UserService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Green写代码
 * @date 2024-02-03 19:09
 */
public class ApiTest {
    private ResourceLoader resourceLoader;

    @Before
    public void init() {
        resourceLoader = new DefaultResourceLoader();
    }

    /**
     * 配置文件读取测试
     */
    @Test
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

    @Test
    public void testXml() throws InstantiationException, IllegalAccessException, BeanException {
        //初始化BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory(CglibSubclassingInstantiationStrategy.class);
        //读取配置文件，注册bean
        BeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");
        //获取Bean，测试
        UserService userService = beanFactory.getBean("userService", UserService.class);
        userService.queryUserInfo(1L);
        userService.queryUserInfo(3L);
        userService.queryUserInfo(5L);
        userService.queryUserInfo(114514L);
    }
    @Test
    public void test() {
        ClasspathXmlApplicationContext applicationContext = new ClasspathXmlApplicationContext();
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.queryUserInfo(1L);
        userService.queryUserInfo(2L);
        userService.queryUserInfo(114L);
    }
    @Test
    public void test2() {
        //初始化BeanFactory
        ClasspathXmlApplicationContext context = new ClasspathXmlApplicationContext("classpath:spring.xml");
        context.registerShutdownHook();

        //获取bean，测试
        UserService userService = context.getBean("userService", UserService.class);
        userService.queryUserInfo(1L);
        userService.queryUserInfo(4L);
        userService.queryUserInfo(114L);
    }
    @Test
    public void test3() {
        //初始化BeanFactory
        ClasspathXmlApplicationContext context = new ClasspathXmlApplicationContext("classpath:spring.xml");
        context.registerShutdownHook();

        //获取bean，测试
        UserService userService = context.getBean("userService", UserService.class);
        userService.queryUserInfo(1L);
        userService.queryUserInfo(4L);
        userService.queryUserInfo(114L);
        System.out.println("beanFactory:" + userService.getBeanFactory());
        System.out.println("beanClassLoader:" + userService.getBeanClassLoader());
        System.out.println("beanName:" + userService.getBeanName());
        System.out.println("applicationContext:" + userService.getApplicationContext());
    }

}
