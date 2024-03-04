package com.green.spring.test;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Green写代码
 * @date 2024-02-04 01:29
 * 自定义方法拦截器
 */
public class UserDaoInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return methodInvocation.proceed();
        } finally {
            System.out.println("+++++ AOP 方法执行监控 +++++");
            System.out.println("方法名称：" + methodInvocation.getMethod().getName());
            System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
            System.out.println("----- AOP 方法监控结束 -----");
        }
    }
}
