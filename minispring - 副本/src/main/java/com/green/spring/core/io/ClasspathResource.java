package com.green.spring.core.io;

import cn.hutool.core.lang.Assert;
import com.green.spring.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Green写代码
 * @date 2024-02-03 17:42
 */
public class ClasspathResource implements Resource{

    private String path;

    private ClassLoader classLoader;

    public ClasspathResource(String path) {
        this(path, null);
    }

    public ClasspathResource(String path, ClassLoader classLoader) {
        Assert.notNull(path);
        this.path = path;
        this.classLoader = null == classLoader ? ClassUtils.getDefaultClassLoader() : classLoader;
    }

    @Override
    public InputStream getInputStream() throws IOException {

        InputStream is = classLoader.getResourceAsStream(path.substring("classpath:".length()));
        if (null == is) {
            throw new FileNotFoundException(path + "文件未找到");
        }
        return is;
    }

}
