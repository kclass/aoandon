package com.hk.aoandon.util.word.chart;

import com.hk.aoandon.util.word.IBaseReplacer;

/**
 * @author kai.hu
 * @date 2021/5/19 17:43
 */
public class BaseChartReplacer extends IBaseReplacer {
    private static BaseChartReplacer instance;

    static {
        instance = new BaseChartReplacer();
        instance.replacers.add(new ChartReplacer());
    }

    private BaseChartReplacer() {
    }

    public static BaseChartReplacer getInstance() {
        return instance;
    }

    @Override
    public String prefix() {
        return "{{@";
    }

    @Override
    public String suffix() {
        return "}}";
    }
}
