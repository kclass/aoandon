package com.hk.test.designmodel.builder;

/**
 * @author kai.hu
 * @date 2020/10/16 16:49
 */
public class ComputerBuilder extends Builder {
    @Override
    void buildPartA() {
        product.setPartA("搞了一个主机");
    }

    @Override
    void buildPartB() {
        product.setPartB("搞了个显示器");
    }

    @Override
    void buildPartC() {
        product.setPartC("搞了一堆外设");
    }
}
