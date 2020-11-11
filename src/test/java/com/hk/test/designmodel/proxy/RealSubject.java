package com.hk.test.designmodel.proxy;

/**
 * @author kai.hu
 * @date 2020/10/27 10:40
 */
public class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("我是一个真实主题");
    }
}
