package com.hk.aoandon.util.word.chart;

import lombok.Data;

import java.util.List;

/**
 * @author kai.hu
 * @date 2021/5/19 17:46
 */
@Data
public class WordChart<T extends Number> {
    /**
     * 图表宽度
     */
    private double width;

    /**
     * 图表高度
     */
    private double height;

    /**
     * 图表的标题，位于图表上方中央，没有就不展示
     */
    private String title;

    /**
     * x轴展示的数据
     */
    private String[] xAxisData;

    /**
     * y轴数据
     */
    private List<WordChartValue<T>> yAxisDataList;
}
