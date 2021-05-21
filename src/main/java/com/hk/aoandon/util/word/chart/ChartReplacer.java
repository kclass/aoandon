package com.hk.aoandon.util.word.chart;

import com.hk.aoandon.util.word.IReplacer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.XDDFShapeProperties;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.List;

/**
 * @author kai.hu
 * @date 2021/5/19 17:44
 */
public class ChartReplacer implements IReplacer {
    @Override
    public boolean replace(XWPFDocument doc, XWPFParagraph paragraph, XWPFRun run, Object data, String key) throws Exception {
        if (data instanceof WordChart) {
            WordChart chartData = (WordChart) data;

            //创建图表对象
            XWPFChart chart = doc.createChart(run, (int) chartData.getWidth() * Units.EMU_PER_CENTIMETER, (int) chartData.getHeight() * Units.EMU_PER_CENTIMETER);
            chart.setTitleText(chartData.getTitle());
            chart.setTitleOverlay(true);
            chart.getOrAddLegend().setPosition(LegendPosition.BOTTOM);
            XDDFCategoryAxis categoryAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
            categoryAxis.setCrosses(AxisCrosses.AUTO_ZERO);
            XDDFValueAxis valueAxis = chart.createValueAxis(AxisPosition.LEFT);
            valueAxis.setCrosses(AxisCrosses.AUTO_ZERO);
            valueAxis.setCrossBetween(AxisCrossBetween.BETWEEN);
            XDDFCategoryDataSource bottomDataSource = XDDFDataSourcesFactory.fromArray(chartData.getXAxisData(), null);

            List<WordChartValue> yAxisDataList = chartData.getYAxisDataList();
            if (CollectionUtils.isEmpty(yAxisDataList)) {
                return true;
            }
            //柱状图对象，这个对象只能有一个
            XDDFBarChartData barData = null;
            //折线图对象
            XDDFChartData lineData = null;
            for (WordChartValue chartValue : yAxisDataList) {
                if (chartValue == null) {
                    continue;
                }
                ChartTypes chartType = chartValue.getChartType();

                if (ChartTypes.BAR.equals(chartType)) {
                    barData = createBar(barData, chart, categoryAxis, valueAxis, bottomDataSource, chartValue);
                } else if (ChartTypes.LINE.equals(chartType)) {
                    lineData = createLine(lineData, chart, categoryAxis, valueAxis, bottomDataSource, chartValue);
                }
            }

            if (barData != null) {
                barData.setBarDirection(BarDirection.COL);
                chart.plot(barData);
            }
            if (lineData != null) {
                chart.plot(lineData);
            }
        }
        return true;
    }

    /**
     * 创建柱状图
     */
    private XDDFBarChartData createBar(XDDFBarChartData barData, XWPFChart chart, XDDFCategoryAxis categoryAxis, XDDFValueAxis valueAxis, XDDFCategoryDataSource bottomDataSource, WordChartValue chartValue) {
        if (barData == null) {
            barData = (XDDFBarChartData) chart.createData(ChartTypes.BAR, categoryAxis, valueAxis);
        }
        XDDFNumericalDataSource leftDataSource = XDDFDataSourcesFactory.fromArray(chartValue.getYAxisData(), null);
        XDDFBarChartData.Series series = (XDDFBarChartData.Series) barData.addSeries(bottomDataSource, leftDataSource);
        series.setShapeProperties(setSeriesColor(chartValue.getSeriesColor()));
        if (chartValue.getSeriesName() != null) {
            series.setTitle(chartValue.getSeriesName(), null);
        }
        series.plot();
        return barData;
    }

    /**
     * 创建折线图
     */
    private XDDFChartData createLine(XDDFChartData lineData, XWPFChart chart, XDDFCategoryAxis categoryAxis, XDDFValueAxis valueAxis, XDDFCategoryDataSource bottomDataSource, WordChartValue chartValue) {
        if (lineData == null) {
            lineData = chart.createData(ChartTypes.LINE, categoryAxis, valueAxis);
        }
        XDDFNumericalDataSource leftDataSource = XDDFDataSourcesFactory.fromArray(chartValue.getYAxisData(), null);
        XDDFLineChartData.Series series = (XDDFLineChartData.Series) lineData.addSeries(bottomDataSource, leftDataSource);
        series.setSmooth(chartValue.isSmooth());
        if (chartValue.getMarkerStyle() != null) {
            series.setMarkerStyle(chartValue.getMarkerStyle());
        } else {
            series.setMarkerStyle(MarkerStyle.NONE);
        }
        series.setShapeProperties(setSeriesColor(chartValue.getSeriesColor()));
        if (chartValue.getSeriesName() != null) {
            series.setTitle(chartValue.getSeriesName(), null);
        }
        series.plot();
        return lineData;
    }

    /**
     * 设置系列的颜色
     *
     * @param color 颜色
     * @return 颜色配置
     */
    private XDDFShapeProperties setSeriesColor(String color) {
        if (color == null) {
            return null;
        }
        XDDFSolidFillProperties fillMarker = new XDDFSolidFillProperties(XDDFColor.from(color.getBytes()));
        XDDFShapeProperties propertiesMarker = new XDDFShapeProperties();
        propertiesMarker.setFillProperties(fillMarker);
        return propertiesMarker;
    }
}
