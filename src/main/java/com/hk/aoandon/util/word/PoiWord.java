package com.hk.aoandon.util.word;

import com.hk.aoandon.util.WordUtil;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author kai.hu
 * @date 2020/11/7 9:48
 */
public class PoiWord {
    private List<WordReplacer> wordReplacers = null;

    private List<BaseWordTableReplacer> tableReplacers = null;

    private List<BaseWordChartReplacer> chartReplacers = null;

    private PoiWord() {}

    public PoiWord(List<WordReplacer> wordReplacers, List<BaseWordTableReplacer> tableReplacers, List<BaseWordChartReplacer> chartReplacers) {
        this.wordReplacers = wordReplacers;
        this.tableReplacers = tableReplacers;
        this.chartReplacers = chartReplacers;
    }

    public void exportToWord(String returnUrl, String templateUrl, Map<String, Object> data) throws Exception {
        FileInputStream fis = new FileInputStream(templateUrl);
        XWPFDocument doc = new XWPFDocument(fis);

        // 替换word模板数据
        replaceAll(doc, data);

        // 保存结果文件
        FileOutputStream fos = null;
        try {
            File file = new File(returnUrl);
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);
            doc.write(fos);

            WordUtil.wordToPdf(doc, "D:\\result.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fis.close();
            if (fos != null) {
                fos.close();
            }
        }
    }

    private void replaceAll(XWPFDocument doc, Map<String, Object> data) throws Exception {
        doParagraphs(doc, data);
        doCharts(doc, data);
    }

    private void doCharts(XWPFDocument doc, Map<String, Object> data) {
        //图表对象
        List<POIXMLDocumentPart> relations = doc.getRelations();
        for (POIXMLDocumentPart poixmlDocumentPart : relations) {
            // 如果是图表元素
            if (poixmlDocumentPart instanceof XWPFChart) {
                // 获取图表对应的表格数据里面的第一行第一列数据，可以拿来当作key值
                String key = PoiWordTools.getZeroData(poixmlDocumentPart).trim();
                if (chartReplacers == null) {
                    return;
                }
                Object chartData = data.get(key);
                if (chartData != null) {
                    boolean replaceFlag;
                    for (BaseWordChartReplacer chartReplacer : chartReplacers) {
                        replaceFlag = chartReplacer.replaceChart(poixmlDocumentPart, data.get(key));
                        if (replaceFlag) {
                            break;
                        }
                    }
                }
            }
        }
    }

    private void doParagraphs(XWPFDocument doc, Map<String, Object> data) throws Exception {
        List<XWPFParagraph> paragraphList = doc.getParagraphs();
        if (paragraphList == null || paragraphList.size() == 0) {
            return;
        }

        for (XWPFParagraph paragraph : paragraphList) {
            List<XWPFRun> runs = paragraph.getRuns();
            for (XWPFRun run : runs) {
                String text = run.getText(0);
                System.out.println(text);
                //初步过滤
                if (text == null || !text.contains("{")) {
                    continue;
                }

                //是否替换标识
                boolean replaceFlag = false;
                //普通文本替换
                if (wordReplacers != null) {
                    for (WordReplacer replacer : wordReplacers) {
                        replaceFlag = replacer.replace(text, data, run);
                        if (replaceFlag) {
                            break;
                        }
                    }
                }

                //表格替换
                if (!replaceFlag && tableReplacers != null) {
                    for (BaseWordTableReplacer tableReplacer : tableReplacers) {
                        replaceFlag = tableReplacer.replaceTable(text, data, paragraph, run, doc);
                        if (replaceFlag) {
                            break;
                        }
                    }
                }
            }
        }
    }
}
