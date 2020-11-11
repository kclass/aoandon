package com.hk.test.designmodel.factory.abstractfactory;

/**
 * @author kai.hu
 * @date 2020/10/16 16:22
 */
public class CreateFactoryB implements AbstractFactory {
    @Override
    public Animal crateAnimal() {
        System.out.println("工厂b养了一头马");
        return new Horse();
    }

    @Override
    public Plant createPlant() {
        System.out.println("工厂A种植了一片白菜");
        return new Cabbage();
    }
}
