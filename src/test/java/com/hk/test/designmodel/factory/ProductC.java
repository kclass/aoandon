package com.hk.test.designmodel.factory;

import com.hk.test.designmodel.factory.Product;
import lombok.Data;

/**
 * @author kai.hu
 * @date 2020/10/16 15:41
 */
@Data
public class ProductC implements Product {
    private String name;

    @Override
    public void produce() {
        System.out.println("我是产品A");
    }
}
