package com.hk.aoandon.util.word.table;

import com.hk.aoandon.util.word.IReplacer;
import com.hk.aoandon.util.word.enums.TableModelEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;

import java.util.List;

/**
 * @author kai.hu
 * @date 2021/5/19 16:24
 */
public class TableReplacer implements IReplacer {
    @Override
    public boolean replace(XWPFDocument doc, XWPFParagraph paragraph, XWPFRun run, Object data, String key) throws Exception {
        if (data instanceof WordTable) {
            WordTable tableData = (WordTable) data;
            //创建表格
            CTP ctp = paragraph.getCTP();
            XmlCursor cursor = ctp.newCursor();
            XWPFTable table = doc.insertNewTbl(cursor);

            if (TableModelEnum.NORMAL.equals(tableData.getTypeEnum())) {
                normalReplace(table, tableData);
            } else if (TableModelEnum.GAP.equals(tableData.getTypeEnum())) {
                gapReplace(table, tableData);
            }
        }

        return true;
    }

    /**
     * 普通类型的表格创建
     *
     * @param table     表格对象
     * @param tableData 表格数据
     */
    private void normalReplace(XWPFTable table, WordTable tableData) {
        List<List<String>> data = tableData.getData();
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        //表头
        createFirstHead(table, data.get(0));
        //数据体
        for (int i = 1; i < data.size(); i++) {
            createDataRow(table, data.get(i), false);
        }
    }

    /**
     * 表头间隔类型的表格创建
     *
     * @param table     表格对象
     * @param tableData 表格数据
     */
    private void gapReplace(XWPFTable table, WordTable tableData) {
        List<List<String>> data = tableData.getData();
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            if (i == 0) {
                createFirstHead(table, data.get(i));
            } else {
                createDataRow(table, data.get(i), i % 2 == 0);
            }
        }
    }

    /**
     * 创建第二及以后的行
     *
     * @param table    表格对象
     * @param data     数据
     * @param headFlag 是否是表头
     */
    private void createDataRow(XWPFTable table, List<String> data, boolean headFlag) {
        XWPFTableRow row = table.createRow();
        for (int i = 0; i < data.size(); i++) {
            XWPFTableCell cell = row.getCell(i);
            cell.setText(data.get(i));
            if (headFlag) {
                setHeadCellStyle(cell);
            }
        }
    }

    /**
     * 创建第一个表头
     *
     * @param table 表格对象
     * @param head  头数据
     */
    private void createFirstHead(XWPFTable table, List<String> head) {
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        cell.setText(head.get(0));
        setHeadCellStyle(cell);

        for (int i = 1; i < head.size(); i++) {
            cell = row.createCell();
            setHeadCellStyle(cell);
            cell.setText(head.get(i));
        }
    }

    /**
     * 设置表头的单元格样式
     *
     * @param cell 单元格对象
     */
    private void setHeadCellStyle(XWPFTableCell cell) {
        cell.setColor("DCDCDC");
    }
}
