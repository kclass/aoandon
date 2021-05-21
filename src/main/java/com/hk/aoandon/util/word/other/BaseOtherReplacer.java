package com.hk.aoandon.util.word.other;

import com.hk.aoandon.util.word.IBaseReplacer;

/**
 * @author kai.hu
 * @date 2021/5/20 10:36
 */
public class BaseOtherReplacer extends IBaseReplacer {
    private static BaseOtherReplacer instance;

    //实现不确定，需要自己添加替换器
    static {
        instance = new BaseOtherReplacer();
    }

    private BaseOtherReplacer() {
    }

    public static BaseOtherReplacer getInstance() {
        return instance;
    }

    @Override
    public String prefix() {
        return "{{?";
    }

    @Override
    public String suffix() {
        return "}}";
    }
}
