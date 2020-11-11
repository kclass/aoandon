package com.hk.test.designmodel.factory.simpleFactory;

import com.hk.test.designmodel.factory.Product;
import com.hk.test.designmodel.factory.ProductA;
import com.hk.test.designmodel.factory.ProductB;
import com.hk.test.designmodel.factory.ProductC;

/**
 * @author kai.hu
 * @date 2020/10/16 15:42
 */
public class SimpleFactory {
    public static final int PRODUCT_A = 1;
    public static final int PRODUCT_B = 2;
    public static final int PRODUCT_C = 3;

    public static Product createProDuct(int productType) {
        switch (productType) {
            case PRODUCT_A:
                return new ProductA();
            case PRODUCT_B:
                return new ProductB();
            case PRODUCT_C:
                return new ProductC();
            default:
                throw new RuntimeException("不存在的产品：" + productType);
        }
    }
}
