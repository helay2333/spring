package com.green.spring.context;

import com.green.spring.beans.factory.Aware;
import com.green.spring.beans.factory.exception.BeanException;

/**
 * @author Green写代码
 * @date 2024-02-03 22:09
 */
public interface ApplicationContextAware extends Aware {
    /**
     * 设置ApplicationContext
     *
     * @param applicationContext applicationContext
     * @throws BeanException applicationContext中的方法可能会抛出此异常
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeanException;

}
