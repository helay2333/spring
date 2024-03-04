package com.green.spring.aop;

/**类匹配接口，用于切点找到给定的接口和目标类
 * @author Green写代码
 * @date 2024-02-04 01:13
 */
public interface ClassFilter {
    /**
     * 是否匹配
     * @param clazz 匹配的类
     * @return
     */
    boolean matches(Class<?> clazz);
}
