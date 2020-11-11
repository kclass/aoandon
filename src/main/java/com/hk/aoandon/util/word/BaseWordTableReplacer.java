package com.hk.aoandon.util.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 表单替换器
 *
 * @author kai.hu
 * @date 2020/11/7 10:35
 */
public abstract class BaseWordTableReplacer {
    public WordTableStyle style;

    public BaseWordTableReplacer(WordTableStyle wordTableStyle) {
        if (wordTableStyle == null) {
            wordTableStyle = new WordTableStyle();
        }
        this.style = wordTableStyle;
    }

    /**
     * 识别表格位置
     *
     * @param text      内容
     * @param data      数据
     * @param paragraph 段落对象
     * @param run       内容添加
     * @param doc       word文档对象
     */
    public boolean replaceTable(String text, Map<String, Object> data, XWPFParagraph paragraph, XWPFRun run, XWPFDocument doc) throws Exception {
        if (!text.contains("${")) {
            return false;
        }
        String key = text.replaceAll("\\$\\{", "").replaceAll("}", "");
        if (!StringUtils.isEmpty(key)) {
            Object tableData = data.get(key);
            if (tableData == null) {
                return false;
            }
            run.setText("", 0);
            return createTable(doc, paragraph, tableData, key);
        }
        return false;
    }

    /**
     * 可以重写这个方法，根据key判断，设置不同的样式，
     * data数据格式不是默认的也可以自己处理
     *
     * @param doc       文档对象
     * @param paragraph 段落对象
     * @param data      数据
     * @param key       标识
     * @return 是否成功替换
     * @throws Exception 可能出现的异常
     */
    protected abstract boolean createTable(XWPFDocument doc, XWPFParagraph paragraph, Object data, String key) throws Exception;
}
