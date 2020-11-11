package com.hk.test.designmodel.factory.factorymethod;

import com.hk.test.designmodel.factory.Product;
import com.hk.test.designmodel.factory.ProductB;

/**
 * @author kai.hu
 * @date 2020/10/16 16:02
 */
public class ProductFactoryB implements ProductFactory {
    @Override
    public Product createProduct() {
        return new ProductB();
    }
}
