package com.hk.aoandon.util.word;

import com.hk.aoandon.util.word.chart.BaseChartReplacer;
import com.hk.aoandon.util.word.enums.WordTypeEnum;
import com.hk.aoandon.util.word.picture.BasePictureReplacer;
import com.hk.aoandon.util.word.table.BaseTableReplacer;
import com.hk.aoandon.util.word.text.BaseTextReplacer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

/**
 * @author kai.hu
 * @date 2020/11/7 9:48
 */
public class PoiWord {
    /**
     * 各种替换器
     */
    private Map<WordTypeEnum, IBaseReplacer> replacerMap = new LinkedHashMap<>();

    /**
     * 添加或者替换注册器
     *
     * @param type         word类型
     * @param baseReplacer 替换器
     */
    public PoiWord registerReplacer(WordTypeEnum type, IBaseReplacer baseReplacer) {
        replacerMap.put(type, baseReplacer);
        return this;
    }

    /**
     * 整个替换替换器
     *
     * @param replacerMap 替换器
     */
    public PoiWord replaceAllReplacer(Map<WordTypeEnum, IBaseReplacer> replacerMap) {
        this.replacerMap = replacerMap;
        return this;
    }

    /**
     * 默认的替换器
     */
    public PoiWord defaultRegister() {
        replacerMap.put(WordTypeEnum.TEXT, BaseTextReplacer.getInstance());
        replacerMap.put(WordTypeEnum.PICTURE, BasePictureReplacer.getInstance());
        replacerMap.put(WordTypeEnum.TABLE, BaseTableReplacer.getInstance());
        replacerMap.put(WordTypeEnum.CHART, BaseChartReplacer.getInstance());
        return this;
    }

    /**
     * 替换模板数据并导出word
     *
     * @param returnUrl 导出word文件位置
     * @param in        模板文件
     * @param data      数据
     * @throws Exception 可能发生的异常
     */
    public void exportToWord(String returnUrl, FileInputStream in, Map<String, Object> data) throws Exception {
        XWPFDocument doc = new XWPFDocument(in);
        // 替换word模板数据
        doParagraphs(doc, data);
        // 保存结果文件
        FileOutputStream fos = null;
        try {
            File file = new File(returnUrl);
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);
            doc.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 替换模板数据并导出word
     *
     * @param returnUrl   导出word文件位置
     * @param templateUrl 模板文件位置
     * @param data        数据
     * @throws Exception 可能发生的异常
     */
    public void exportToWord(String returnUrl, String templateUrl, Map<String, Object> data) throws Exception {
        FileInputStream fis = new FileInputStream(templateUrl);
        exportToWord(returnUrl, fis, data);
    }

    /**
     * 替换数据的逻辑
     *
     * @param doc  文档对象
     * @param data 数据
     * @throws Exception 可能发生的异常
     */
    private void doParagraphs(XWPFDocument doc, Map<String, Object> data) throws Exception {
        List<XWPFParagraph> paragraphList = doc.getParagraphs();
        if (CollectionUtils.isEmpty(paragraphList)) {
            return;
        }

        for (XWPFParagraph paragraph : paragraphList) {
            List<XWPFRun> runs = paragraph.getRuns();
            for (XWPFRun run : runs) {
                String text = run.getText(0);
                System.out.println(text);
                //初步过滤
                if (text == null) {
                    continue;
                } else {
                    text = text.trim();
                }
                //是否替换标识
                boolean replaceFlag = false;
                for (IBaseReplacer baseReplacer : replacerMap.values()) {
                    if (!text.startsWith(baseReplacer.prefix()) || !text.endsWith(baseReplacer.suffix())) {
                        continue;
                    }

                    //先将此处置为空
                    run.setText("", 0);
                    text = text.replace(baseReplacer.prefix(), "").replace(baseReplacer.suffix(), "");
                    List<IReplacer> replacers = baseReplacer.replacers;
                    if (CollectionUtils.isEmpty(replacers)) {
                        continue;
                    }
                    for (IReplacer replacer : replacers) {
                        if (replacer == null) {
                            continue;
                        }
                        replaceFlag = replacer.replace(doc, paragraph, run, data.get(text), text);
                        if (replaceFlag) {
                            break;
                        }
                    }
                    if (replaceFlag) {
                        break;
                    }
                }
            }
        }
    }
}
