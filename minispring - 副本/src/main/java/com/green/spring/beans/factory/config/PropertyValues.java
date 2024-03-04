package com.green.spring.beans.factory.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Green写代码
 * @date 2024-02-03 15:28
 */
public class PropertyValues {

    private Map<String, PropertyValue> propertyValueMap = new HashMap<>();

    public void addPropertyValue(PropertyValue propertyValue) {
        propertyValueMap.put(propertyValue.getName(), propertyValue);
    }

    public PropertyValue getPropertyValue(String name) {
        return propertyValueMap.get(name);
    }

    public PropertyValue[] getPropertyValues() {
        return propertyValueMap.values().toArray(new PropertyValue[0]);
    }
}

