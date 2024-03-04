package com.green.spring.beans.factory;

public interface InitializingBean {
    /**
     * 处理完属性后调用
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;
}
