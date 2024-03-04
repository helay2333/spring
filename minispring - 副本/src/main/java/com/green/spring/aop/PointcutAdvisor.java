package com.green.spring.aop;



/**
 * @author Green写代码
 * @date 2024-02-04 01:39
 * 切点的建议者
 */
public interface PointcutAdvisor extends Advisor{
    Pointcut getPointcut();
}
