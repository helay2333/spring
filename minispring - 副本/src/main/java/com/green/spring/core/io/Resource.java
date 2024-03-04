package com.green.spring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Green写代码
 * @date 2024-02-03 17:42
 */
public interface Resource {

    InputStream getInputStream() throws IOException;
}
