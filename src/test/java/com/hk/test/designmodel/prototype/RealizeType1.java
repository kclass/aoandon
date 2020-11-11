package com.hk.test.designmodel.prototype;

import lombok.Data;

/**
 * @author kai.hu
 * @date 2020/10/15 11:46
 */
@Data
public class RealizeType1 implements Cloneable {
    private String value1;

    private ObjectDemo1 objectDemo1;

    @Override
    public RealizeType1 clone() throws CloneNotSupportedException {
        return (RealizeType1) super.clone();
    }
}
