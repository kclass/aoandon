package com.hk.aoandon.util.word.picture;

import com.hk.aoandon.util.word.IBaseReplacer;

/**
 * @author kai.hu
 * @date 2021/5/19 14:28
 */
public class BasePictureReplacer extends IBaseReplacer {
    private static BasePictureReplacer instance;

    static {
        instance = new BasePictureReplacer();
        instance.replacers.add(new PictureReplacer());
    }

    private BasePictureReplacer() {
    }

    public static BasePictureReplacer getInstance() {
        return instance;
    }

    @Override
    public String prefix() {
        return "{{#";
    }

    @Override
    public String suffix() {
        return "}}";
    }
}
