package com.hk.test.designmodel.factory.abstractfactory;

/**
 * @author kai.hu
 * @date 2020/10/16 16:22
 */
public class CreateFactoryA implements AbstractFactory {
    @Override
    public Animal crateAnimal() {
        System.out.println("工厂A养了一头牛");
        return new Cattle();
    }

    @Override
    public Plant createPlant() {
        System.out.println("工厂A种植了一颗苹果树");
        return new Apple();
    }
}
