package com.green.spring.beans;

/**
 * @author Green写代码
 * @date 2024-02-03 20:56
 */
public interface DisposableBean {
    void destroy() throws Exception;
}
