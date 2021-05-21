package com.hk.aoandon.util.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * @author kai.hu
 * @date 2021/5/19 11:49
 */
public interface IReplacer {
    /**
     * 替换关键字处的方法
     *
     * @param doc       文档对象
     * @param paragraph 段落对象
     * @param run       位置对象
     * @param data      数据，可能为空
     * @param key       关键字
     * @return 是否替换，若返回false,会继续找下一个替换，若返回true,表示该处替换成功，继续找下一个标识
     * @throws Exception 可能会发生的异常
     */
    boolean replace(XWPFDocument doc, XWPFParagraph paragraph, XWPFRun run, Object data, String key) throws Exception;
}
