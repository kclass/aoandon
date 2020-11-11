package com.hk.aoandon.util.word;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.util.Map;

/**
 * 默认支持的单图片替换器
 *
 * @author kai.hu
 * @date 2020/11/7 9:36
 */
@Log4j2
public class WordImageReplacer implements WordReplacer {
    @Override
    public boolean replace(String text, Map<String, Object> data, XWPFRun run) {
        if (!text.contains("{{@")) {
            return false;
        }
        // 替换图片内容 参考：https://blog.csdn.net/a909301740/article/details/84984445
        String imgKey = text.replaceAll("\\{\\{@", "").replaceAll("}}", "");
        if (!StringUtils.isEmpty(data.get(imgKey))) {
            WordImage imgData = (WordImage) data.get(imgKey);
            try {
                run.setText(StringUtils.isEmpty(imgData.getTitle()) ? "" : "\n" + imgData.getTitle(), 0);
                run.addPicture(StringUtils.isEmpty(imgData.getFilePath()) ? imgData.getImageData() : new FileInputStream(imgData.getFilePath()),
                        Document.PICTURE_TYPE_PNG,
                        "img.png",
                        Units.toEMU(imgData.getWidth()),
                        Units.toEMU(imgData.getHeight()));
            } catch (Exception e) {
                log.warn("图片替换出错：" + imgKey);
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }
}
