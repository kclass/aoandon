package com.hk.aoandon.util.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kai.hu
 * @date 2020/11/9 11:43
 */
public class DefaultWordTableReplacer extends BaseWordTableReplacer {
    public DefaultWordTableReplacer(WordTableStyle wordTableStyle) {
        super(wordTableStyle);
    }

    @SuppressWarnings("all")
    @Override
    protected boolean createTable(XWPFDocument doc, XWPFParagraph paragraph, Object data, String key) throws Exception {
        CTP ctp = paragraph.getCTP();
        XmlCursor cursor = ctp.newCursor();
        // ---这个是关键
        XWPFTable tableOne = doc.insertNewTbl(cursor);
        // 设置表格宽度，第一行宽度就可以了，这个值的单位，目前我也还不清楚，还没来得及研究
        tableOne.setWidth(8500);

        List<Map<String, Object>> tableData = (List<Map<String, Object>>) data;

        //表头,认为第一行数据是表头,要想保证有序，表头可以用LinkedHashMap存储
        Map<String, Object> headData = tableData.get(0);
        XWPFTableRow tableOneRowOne = tableOne.getRow(0);
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, Object> entry : headData.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            if (keys.size() == 0) {
                style.setHeadStyle(tableOneRowOne.getCell(0), v == null ? "" : v.toString());
            } else {
                style.setHeadStyle(tableOneRowOne.createCell(), v == null ? "" : v.toString());
            }
            keys.add(k);
        }

        //内容
        for (int i = 1; i < tableData.size(); i++) {
            // 表格第二行
            XWPFTableRow tableOneRowTwo = tableOne.createRow();
            Map<String, Object> contentData = tableData.get(i);
            for (int j = 0; j < keys.size(); j++) {
                Object content = contentData.get(keys.get(j));
                style.setBodyStyle(tableOneRowTwo.getCell(j), content == null ? "" : content.toString());
            }
        }

        return true;
    }
}
