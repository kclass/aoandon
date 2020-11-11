package com.hk.test.designmodel.factory.abstractfactory;

/**
 * @author kai.hu
 * @date 2020/10/16 16:17
 */
public class Cattle implements Animal {
    @Override
    public void produce() {
        System.out.println("生产了一头🐂");
    }
}
