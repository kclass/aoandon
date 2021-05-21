package com.hk.aoandon.util.word.table;

import com.hk.aoandon.util.word.IBaseReplacer;

/**
 * @author kai.hu
 * @date 2021/5/19 16:22
 */
public class BaseTableReplacer extends IBaseReplacer {
    private static BaseTableReplacer instance;

    static {
        instance = new BaseTableReplacer();
        instance.replacers.add(new TableReplacer());
    }

    private BaseTableReplacer() {
    }

    public static BaseTableReplacer getInstance() {
        return instance;
    }

    @Override
    public String prefix() {
        return "${";
    }

    @Override
    public String suffix() {
        return "}";
    }
}
