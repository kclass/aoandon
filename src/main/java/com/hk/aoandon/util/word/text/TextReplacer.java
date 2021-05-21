package com.hk.aoandon.util.word.text;

import com.hk.aoandon.util.word.IReplacer;
import lombok.Data;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * @author kai.hu
 * @date 2021/5/19 13:44
 */
@Data
public class TextReplacer implements IReplacer {
    @Override
    public boolean replace(XWPFDocument doc, XWPFParagraph paragraph, XWPFRun run, Object data, String key) {
        run.setText(data == null ? "" : data.toString(), 0);
        return true;
    }
}
