package com.green.spring.context;

import com.green.spring.beans.factory.exception.BeanException;

/**
 * @author Green写代码
 * @date 2024-02-03 19:53
 */
public interface ConfigurableApplicationContext extends ApplicationContext{
    /**
     *刷新容器
     * @throws BeanException
     */
    void refresh() throws BeanException;
    /**
     * 注册关闭的虚拟机钩子
     */
    void registerShutdownHook();

    /**
     * 手动执行关闭
     */
    void close();
}
