import com.green.spring.aop.AdvisedSupport;
import com.green.spring.aop.TargetSource;
import com.green.spring.aop.aspect.AspectJExpressionPointcut;
import com.green.spring.aop.framework.Cglib2AopProxy;
import com.green.spring.aop.framework.JdkDynamicAopProxy;
import com.green.spring.context.support.ClasspathXmlApplicationContext;
import com.green.spring.test.IUserDao;
import com.green.spring.test.UserDao;
import com.green.spring.test.UserDaoInterceptor;
import org.junit.Test;

/**
 * @author Green写代码
 * @date 2024-02-04 01:29
 */
public class AopTest {
    @Test
    public void test(){
        IUserDao userDao = new UserDao();

        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userDao));
        advisedSupport.setMethodInterceptor(new UserDaoInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* com.green.spring.test.IUserDao.*(..))"));

        IUserDao proxyJdk = (IUserDao) new JdkDynamicAopProxy(advisedSupport).getProxy();

        assert proxyJdk != null;
        System.out.println("测试结果：" + proxyJdk.queryUserName(1L));

        IUserDao proxyCglib = (IUserDao) new Cglib2AopProxy(advisedSupport).getProxy();

        System.out.println("测试结果：" + proxyCglib.queryUserName(2L));
    }

    /**
     * 实现和Spring强关联
     */
    @Test
    public void test2() {
        ClasspathXmlApplicationContext context = new ClasspathXmlApplicationContext("classpath:spring.xml");
        IUserDao userDao = context.getBean("userDao", IUserDao.class);
        System.out.println(userDao.queryUserName(1L));
    }
}
