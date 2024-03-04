package com.green.spring.context.support;

/**
 * @author Green写代码
 * @date 2024-02-03 19:53
 */

public class ClasspathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] configLocations;

    public ClasspathXmlApplicationContext() {
        this("classpath:spring.xml");
    }

    public ClasspathXmlApplicationContext(String configLocation) {
        this(new String[]{configLocation});
    }

    public ClasspathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}

