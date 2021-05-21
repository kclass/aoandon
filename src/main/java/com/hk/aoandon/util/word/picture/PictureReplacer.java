package com.hk.aoandon.util.word.picture;

import com.hk.aoandon.util.word.IReplacer;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author kai.hu
 * @date 2021/5/19 14:31
 */
@Data
public class PictureReplacer implements IReplacer {
    @Override
    public boolean replace(XWPFDocument doc, XWPFParagraph paragraph, XWPFRun run, Object data, String key) throws Exception {

        if (data instanceof WordImage) {
            WordImage img = (WordImage) data;
            replacePic(run, img, 0);
        } else if (data instanceof List) {
            List<WordImage> list = (List<WordImage>) data;
            for (int i = 0; i < list.size(); i++) {
                replacePic(run, list.get(i), i);
            }
        }
        return true;
    }

    /**
     * 替换图片
     *
     * @param run      文档位置对象
     * @param img      图片对象
     * @param position 图片位置
     * @throws IOException io异常
     */
    private void replacePic(XWPFRun run, WordImage img, int position) throws IOException {
        InputStream is = null;
        String title = img.getTitle();
        if (StringUtils.isNotEmpty(title)) {
            if (position == 0) {
                run.setText(title, 0);
            } else {
                run.setText(title);
            }
        }

        try {
            run.addPicture(is = img.getImageIn() == null ? new FileInputStream(img.getFilePath()) : img.getImageIn(),
                    Document.PICTURE_TYPE_JPEG, "img.jpeg",
                    (int) img.getWidth() * Units.EMU_PER_CENTIMETER,
                    (int) img.getHeight() * Units.EMU_PER_CENTIMETER);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("图片替换出错");
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
