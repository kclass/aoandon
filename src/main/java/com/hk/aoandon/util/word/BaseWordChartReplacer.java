package com.hk.aoandon.util.word;

import org.apache.poi.ooxml.POIXMLDocumentPart;

/**
 * 默认的图标替换器
 *
 * @author kai.hu
 * @date 2020/11/9 11:04
 */
public interface BaseWordChartReplacer {
    /**
     * 替换图标表的方法
     *
     * @param poixmlDocumentPart 图标对象
     * @param data               数据
     * @return  是否替换了
     */
    boolean replaceChart(POIXMLDocumentPart poixmlDocumentPart, Object data);
}
