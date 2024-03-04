package com.green.spring.beans.factory.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Green写代码
 * @date 2024-02-03 14:08
 */
@Data
@Getter
@Setter
public class BeanDefinition {
    private final Class<?> beanCLass;

    private PropertyValues propertyValues = new PropertyValues();;

    //初始化名称
    private String initMethodName;
    //销毁名称
    private String destroyMethodName;

    /**
     * bean作用范围，默认为单例
     */
    private String scope = ConfigurableBeanFactory.SCOPE_SINGLETON;

    /**
     * 默认启用单例模式
     */
    private boolean singleton = true;

    /**
     * 默认不适用原型模式
     */
    private boolean prototype = false;
    public BeanDefinition(Class<?> beanCLass) {
        this.beanCLass = beanCLass;
    }
    public BeanDefinition(Class<?> beanCLass, PropertyValues propertyValues) {
        this.beanCLass = beanCLass;
        this.propertyValues = propertyValues;
    }
    public Class<?> getBeanClass(){
        return beanCLass;
    }
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = ConfigurableBeanFactory.SCOPE_SINGLETON.equals(scope);
        this.prototype = ConfigurableBeanFactory.SCOPE_PROTOTYPE.equals(scope);
    }

}
