package com.hk.aoandon.util.word.table;

import com.hk.aoandon.util.word.enums.TableModelEnum;
import lombok.Data;

import java.util.List;

/**
 * @author kai.hu
 * @date 2021/5/19 17:04
 */
@Data
public class WordTable {
    /**
     * 表格宽度
     */
    private double tableWidth;

    /**
     * 表格模式
     */
    private TableModelEnum typeEnum;

    /**
     * 表格数据
     */
    private List<List<String>> data;
}
