package com.green.spring.core.io;

/**
 * @author Green写代码
 * @date 2024-02-03 17:42
 */
public interface ResourceLoader {

    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 根据传入的参数，获取对应的资源
     *
     * @param location location
     * @return resource
     */
    Resource getResource(String location);

}
