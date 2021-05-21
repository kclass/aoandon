package com.hk.aoandon.util.word.text;

import com.hk.aoandon.util.word.IBaseReplacer;

/**
 * @author kai.hu
 * @date 2021/5/19 11:55
 */
public class BaseTextReplacer extends IBaseReplacer {
    private static BaseTextReplacer instance;

    static {
        instance = new BaseTextReplacer();
        instance.replacers.add(new TextReplacer());
    }

    private BaseTextReplacer() {
    }

    public static BaseTextReplacer getInstance() {
        return instance;
    }

    @Override
    public String prefix() {
        return "{{$";
    }

    @Override
    public String suffix() {
        return "}}";
    }
}
