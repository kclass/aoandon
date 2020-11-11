package com.hk.test.designmodel.factory.abstractfactory;

/**
 * @author kai.hu
 * @date 2020/10/16 16:20
 */
public class Cabbage implements Plant {
    @Override
    public void produce() {
        System.out.println("生产了一颗白菜");
    }
}
