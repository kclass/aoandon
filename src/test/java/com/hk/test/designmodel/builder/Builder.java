package com.hk.test.designmodel.builder;

/**
 * @author kai.hu
 * @date 2020/10/16 16:42
 */
public abstract class Builder {
    protected Product product = new Product();

    abstract void buildPartA();

    abstract void buildPartB();

    abstract void buildPartC();

    public Product getProduct() {
        return product;
    }
}
