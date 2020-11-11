package com.hk.test.designmodel.factory.abstractfactory;

/**
 * @author kai.hu
 * @date 2020/10/16 16:19
 */
public class Apple implements Plant {
    @Override
    public void produce() {
        System.out.println("生产了一个🍎");
    }
}
