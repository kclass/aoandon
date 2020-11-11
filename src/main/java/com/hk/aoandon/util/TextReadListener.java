package com.hk.aoandon.util;

import com.csvreader.CsvReader;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kai.hu
 * @date 2020/10/9 17:11
 */
public abstract class TextReadListener {
    /**
     * 标题行数
     */
    private int headLine;

    /**
     * 分隔符
     */
    private char split;

    public TextReadListener(int headLine, char split) {
        this.headLine = headLine;
        this.split = split;
    }

    /**
     * 读取多有数据
     *
     * @param inputStream 数据
     */
    public void parseCsvData(InputStream inputStream) {
        try {
            byte[] bytes = BaseUtil.inputStream2Bytes(inputStream);
            CsvReader csvReader = new CsvReader(new ByteArrayInputStream(bytes), split, Charset.forName(BaseUtil.getFileCharset(bytes)));
            int lineNum = 0;
            while (csvReader.readRecord()) {
                int lineNumCount = csvReader.getColumnCount();
                Map<Integer, String> lineData = new HashMap<>(lineNumCount);
                for (int i = 0; i < lineNumCount; i++) {
                    lineData.put(i, csvReader.get(i));
                }
                if (lineNum < headLine) {
                    parseHeadData(lineData);
                    lineNum++;
                } else {
                    parseLineData(lineData);
                }
            }

            doAfterAllAnalysed();
            csvReader.close();
        } catch (Exception e) {
            onException(e);
        }
    }

    /**
     * 读取多有数据
     *
     * @param filePath 文件路径
     */
    public void parseCsvData(String filePath) {
        try {
            parseCsvData(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            onException(e);
        }
    }

    /**
     * 读取标题数据
     *
     * @param headData 单行数据
     */
    public abstract void parseHeadData(Map<Integer, String> headData);

    /**
     * 读取单行数据
     *
     * @param lineData 单行数据
     */
    public abstract void parseLineData(Map<Integer, String> lineData);

    /**
     * 数据读取完之后做的事
     */
    public abstract void doAfterAllAnalysed();

    /**
     * 发生异常怎么办
     *
     * @param e 异常
     */
    public abstract void onException(Exception e);
}
