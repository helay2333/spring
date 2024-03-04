package com.green.spring.core.io;

import cn.hutool.core.lang.Assert;

import java.net.MalformedURLException;
import java.net.URL;
/**
 * @author Green写代码
 * @date 2024-02-03 17:42
 */
public class DefaultResourceLoader implements ResourceLoader {
    @Override
    public Resource getResource(String location) {
        Assert.notNull(location);
        /**
         * 有没有 classpath:前缀，如果有，就返回 ClasspathResource。
         * 如果没有，则尝试使用 UrlResource。如果有错误，则返回 FileSystemResource
         */
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClasspathResource(location);
        } else {
            try {
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                return new FileSystemResource(location);
            }
        }
    }
}

