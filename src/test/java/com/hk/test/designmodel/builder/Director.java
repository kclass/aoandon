package com.hk.test.designmodel.builder;

/**
 * @author kai.hu
 * @date 2020/10/16 16:41
 */
public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public Product buildProduct() {
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
        return builder.getProduct();
    }
}
