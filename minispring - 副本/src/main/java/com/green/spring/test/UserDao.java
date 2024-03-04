package com.green.spring.test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Green写代码
 * @date 2024-02-03 17:31
 */
public class UserDao implements IUserDao{
//    private static final Map<Long, String> userMap = new HashMap<Long, String>();
//    public void initMethod() {
//        System.out.println("执行UserDao的initMethod");
//        userMap.put(1L, "akitsuki");
//        userMap.put(2L, "toyosaki");
//        userMap.put(3L, "kugimiya");
//        userMap.put(4L, "hanazawa");
//        userMap.put(5L, "momonogi");
//    }
//
//    public void destroyMethod() {
//        System.out.println("执行UserDao的destroyMethod");
//        userMap.clear();
//    }
//
//    public String queryUserName(Long id) {
//        return userMap.get(id);
//    }
private static final Map<Long, String> userMap = new HashMap<>();

    static {
        userMap.put(1L, "akitsuki");
        userMap.put(2L, "toyosaki");
        userMap.put(3L, "kugimiya");
        userMap.put(4L, "hanazawa");
        userMap.put(5L, "momonogi");
    }

    @Override
    public String queryUserName(Long id) {
        return userMap.get(id);
    }
}
