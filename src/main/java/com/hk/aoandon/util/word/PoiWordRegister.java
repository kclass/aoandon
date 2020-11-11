package com.hk.aoandon.util.word;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kai.hu
 * @date 2020/11/7 9:51
 */
@Getter
public class PoiWordRegister {
    private List<WordReplacer> wordReplacers = new ArrayList<>();

    private List<BaseWordTableReplacer> tableReplacers = new ArrayList<>();

    private List<BaseWordChartReplacer> chartReplacers = new ArrayList<>();

    public PoiWordRegister() {
        defaultRegister();
    }

    /**
     * 注册普通替换器
     */
    public void registerPlainWordReplacer(WordReplacer replacer) {
        wordReplacers.add(0, replacer);
    }

    /**
     * 注册表格替换器
     */
    public void registerWordTableReplacer(BaseWordTableReplacer replacer) {
        tableReplacers.add(0, replacer);
    }

    /**
     * 注册图表替换器
     */
    public void registerWordChartReplacer(BaseWordChartReplacer replacer) {
        chartReplacers.add(0, replacer);
    }

    /**
     * 默认的注册器
     */
    private void defaultRegister() {
        //普通文本替换器
        wordReplacers.add(new WordTextReplacer());
        //单图片
        wordReplacers.add(new WordImageReplacer());
        //多图片
        wordReplacers.add(new WordImagesReplacer());
        //默认的表格
        tableReplacers.add(new DefaultWordTableReplacer(null));
        //默认的图表
        chartReplacers.add(new DefaultWordChartReplacer());
    }
}
