package com.hk.aoandon.util.word;

import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author kai.hu
 * @date 2020/11/9 11:39
 */
public class DefaultWordChartReplacer implements BaseWordChartReplacer {
    @Override
    public boolean replaceChart(POIXMLDocumentPart poixmlDocumentPart, Object data) {
        WordChart wordChart = (WordChart) data;
        XWPFChart chart = (XWPFChart) poixmlDocumentPart;
        chart.getCTChart();

        //根据属性第一列名称切换数据类型
        CTChart ctChart = chart.getCTChart();
        CTPlotArea plotArea = ctChart.getPlotArea();


        if (!CollectionUtils.isEmpty(plotArea.getLineChartList())) {
            PoiWordToolsDynamic.replaceLineCharts(poixmlDocumentPart,wordChart.getTitle(), wordChart.getFiledName(), wordChart.getListItem());
        } else if (!CollectionUtils.isEmpty(plotArea.getBarChartList())){
            PoiWordToolsDynamic.replaceBarCharts(poixmlDocumentPart, wordChart.getTitle(), wordChart.getFiledName(), wordChart.getListItem());
        } else {
            return false;
        }

        return true;
    }
}
