package com.hk.test.designmodel.factory.abstractfactory;

/**
 * @author kai.hu
 * @date 2020/10/16 16:21
 */
public interface AbstractFactory {
    Animal crateAnimal();
    Plant createPlant();
}
