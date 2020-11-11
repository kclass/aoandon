package com.hk.test.designmodel.singleton;

/**
 * @author kai.hu
 * @date 2020/10/15 11:22
 */
public class Singleton1 {
    private static final Singleton1 instance = new Singleton1();

    private Singleton1() {}

    public static Singleton1 getInstance() {
        return instance;
    }

    public void getName() {
        System.out.println("我是 张三");
    }
}
