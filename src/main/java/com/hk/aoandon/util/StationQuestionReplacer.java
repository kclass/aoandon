package com.hk.aoandon.util;


import com.hk.aoandon.util.word.WordUtil;
import com.hk.aoandon.vo.CommentVo;
import com.hk.aoandon.vo.ImgVo;
import com.hk.aoandon.vo.StationQuestionVo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.util.CollectionUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author kai.hu
 * @date 2020/11/12 9:37
 */
@Log4j2
public class StationQuestionReplacer{
    /**
     * 文件存放路径
     */
    private String filePath;

    public StationQuestionReplacer(String filePath) {
        this.filePath = filePath;
    }

    public boolean replace(XWPFDocument doc, Map<String, Object> data) throws IOException {
        List<StationQuestionVo> vos = (List<StationQuestionVo>) data.get("stationQuestion");
        XWPFParagraph p = doc.getLastParagraph();
        p.setAlignment(ParagraphAlignment.LEFT);
        p.setSpacingAfterLines(200);
        for (int i = 0; i < vos.size(); i++) {
            StationQuestionVo vo = vos.get(i);
            XWPFRun run = p.createRun();
            run.setStyle("8");
            run.setBold(true);
            run.setText("\t•问题点" + (i + 1) + ":" + vo.getQuestionTitle());
            run.addBreak();
            XWPFRun run1 = p.createRun();
            run1.setStyle("8");
            run1.setText("\t\t问题分类:" + vo.getQuestionSort());
            run1.addBreak();
            run1.setText("\t\t解决情况:" + (vo.getSolveFlag() == null ? "" : vo.getSolveFlag()));
            run1.addBreak();
            addComment("问题描述", vo.getQuestionDescription(), run1);
            run1.addBreak();
            addComment("验证结果", vo.getResult(), run1);
            run1.addBreak();
            addComment("解决方案", vo.getSolution(), run1);
            run1.addBreak();
        }
        return true;
    }

    /**
     * 填加评论类内容
     *
     * @param title     标题
     * @param commentVo 品论对象
     * @param run       写东西的
     * @throws IOException 文件读取异常
     */
    private void addComment(String title, CommentVo commentVo, XWPFRun run) throws IOException {
        run.setText("\t\t" + title + "：");
        if (commentVo == null) {
            return;
        }
        String desc = commentVo.getDesc();
        if (StringUtils.isNotEmpty(desc)) {
            run.setText(desc);
        }
        //评论中的图片
        List<ImgVo> img = commentVo.getImg();
        if (CollectionUtils.isEmpty(img)) {
            return;
        }
        for (ImgVo imgVo : img) {
            FileInputStream fis = null;
            try {
                run.addPicture(fis = new FileInputStream(filePath + imgVo.getImgPath()),Document.PICTURE_TYPE_JPEG,
                        "img.jpeg",
                        Units.toEMU(480),
                        Units.toEMU(240));
            } catch (Exception e) {
                e.printStackTrace();
                log.error("图片替换出错");
            } finally {
                if (fis != null) {
                    fis.close();
                }
            }
        }
    }
}
