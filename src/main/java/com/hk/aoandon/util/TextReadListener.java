package com.hk.aoandon.util;

import java.io.*;
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
    private String split;

    public TextReadListener(int headLine) {
        this.headLine = headLine;
        this.split = ",";
    }

    /**
     * 读取多有数据
     *
     * @param inputStream 数据
     */
    public void parseCsvData(InputStream inputStream){
        try {
            byte[] bytes = BaseUtil.inputStream2Bytes(inputStream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes), BaseUtil.getFileCharset(bytes)));
            int lineNum = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                Map<Integer, String> data = new HashMap<>(32);
                String[] lineData = line.split(this.split);
                for (int i = 0; i < lineData.length; i++) {
                    data.put(i, lineData[i]);
                }
                if (lineNum < headLine) {
                    parseHeadData(data);
                    lineNum++;
                } else {
                    parseLineData(data);
                }
            }
            doAfterAllAnalysed();
            reader.close();
        } catch (Exception e) {
            onException(e);
        }
    }

    /**
     * 读取多有数据
     *
     * @param filePath 文件路径
     */
    public void parseCsvData(String filePath){
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
