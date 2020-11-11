package com.hk.test.designmodel.singleton;

/**
 * @author kai.hu
 * @date 2020/10/15 11:35
 */
public class Singleton2 {
    private static volatile Singleton2 instance = null;

    private Singleton2() {}

    public static synchronized Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        } else {
            System.out.println("有了， 有了");
        }
        return instance;
    }

    public void getName() {
        System.out.println("i am lisi");
    }
}
