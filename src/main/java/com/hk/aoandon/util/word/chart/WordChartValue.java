package com.hk.aoandon.util.word.chart;

import lombok.Data;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.MarkerStyle;

/**
 * @author kai.hu
 * @date 2021/5/19 17:54
 */
@Data
public class WordChartValue<T extends Number> {
    /**
     * 数据的类型
     * 目前只支持BAR和LINE
     */
    private ChartTypes chartType;

    /**
     * y轴的数据
     */
    private T[] yAxisData;

    /**
     * 系列名称
     */
    private String seriesName;

    /**
     * 系列颜色
     */
    private String seriesColor;

    /**
     * 设置折线图是否光滑
     */
    private boolean smooth;

    /**
     * 折线图每个点的标记，不设置默认没有
     */
    private MarkerStyle markerStyle;
}
