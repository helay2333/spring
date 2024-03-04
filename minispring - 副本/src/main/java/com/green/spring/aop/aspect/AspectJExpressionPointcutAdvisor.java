package com.green.spring.aop.aspect;

import com.green.spring.aop.PointcutAdvisor;
import lombok.Data;
import org.aopalliance.aop.Advice;
import com.green.spring.aop.Pointcut;

/**
 * @author Green写代码
 * @date 2024-02-04 01:39
 */
@Data
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
    private AspectJExpressionPointcut pointcut;

    private Advice advice;

    private String expression;
    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        if (null == pointcut) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }
}
