package com.green.spring.beans.factory.config;

import lombok.Getter;
import lombok.Setter;
/**
 * @author Green写代码
 * @date 2024-02-03 15:28
 */
@Getter
@Setter
public class PropertyValue {
    private String name;

    private Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

}
