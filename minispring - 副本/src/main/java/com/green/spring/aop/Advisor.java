package com.green.spring.aop;

import org.aopalliance.aop.Advice;

/**
 * @author Green写代码
 * @date 2024-02-04 01:36
 * 获得到的建议, 建议给谁呢, 给切点
 */
public interface Advisor {
    Advice getAdvice();
}
