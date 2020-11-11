package com.hk.aoandon.util.word;

import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 默认支持的文本替换器
 *
 * @author kai.hu
 * @date 2020/11/7 9:34
 */
public class WordTextReplacer implements WordReplacer {
    @Override
    public boolean replace(String text, Map<String, Object> data, XWPFRun run) {
        if (!text.contains("{{$")) {
            return false;
        }
        String key = text.replaceAll("\\{\\{\\$", "").replaceAll("}}", "");
        if (!StringUtils.isEmpty(data.get(key))) {
            run.setText(data.get(key).toString(), 0);
            return true;
        }
        return false;
    }
}
