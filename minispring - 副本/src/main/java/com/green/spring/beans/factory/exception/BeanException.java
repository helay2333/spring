package com.green.spring.beans.factory.exception;

/**
 * @author Green写代码
 * @date 2024-02-03 14:08
 */
public class BeanException extends RuntimeException{
    public BeanException(String info){
        super(info);
    }
    public BeanException(String info, Exception o){
        super(info, o);
    }
}
