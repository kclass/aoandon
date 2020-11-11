package com.hk.aoandon.util.word;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.Map;

/**
 * @author kai.hu
 * @date 2020/11/7 9:29
 */
public interface WordReplacer {
    /**
     * 替换普通的段落类容，如文本，图片
     * @param text 段落类容
     * @param data 参数
     * @param run 添加内容
     * @return 是否替换
     */
    boolean replace(String text, Map<String, Object> data, XWPFRun run);
}
