package com.hk.aoandon.util.word;

import org.apache.logging.log4j.util.Strings;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author kai.hu
 * @date 2020/11/7 10:27
 */
public class WordTableStyle {
    private static final BigDecimal bd2 = new BigDecimal("2");

    public void setHeadStyle(XWPFTableCell cell, String content) {
        setWordCellSelfStyle(cell, "微软雅黑", "9", 0, "left", "top", "#000000", "#B4C6E7", null, content);
    }

    public void setBodyStyle(XWPFTableCell cell, String content) {
        setWordCellSelfStyle(cell, "微软雅黑", "9", 0, "left", "top", "#000000", "#ffffff", null, content);
    }

    private static void setWordCellSelfStyle(XWPFTableCell cell, String fontName, String fontSize, int fontBold,
                                            String alignment, String vertical, String fontColor,
                                            String bgColor, String cellWidth, String content) {

        //poi对字体大小设置特殊，不支持小数，但对原word字体大小做了乘2处理
        BigInteger bFontSize = new BigInteger("24");
        if (Strings.isNotBlank(fontSize)) {
            //poi对字体大小设置特殊，不支持小数，但对原word字体大小做了乘2处理
            BigDecimal fontSizeBd = new BigDecimal(fontSize);
            fontSizeBd = bd2.multiply(fontSizeBd);
            //这里取整
            fontSizeBd = fontSizeBd.setScale(0, BigDecimal.ROUND_HALF_UP);
            bFontSize = new BigInteger(fontSizeBd.toString());
        }

        // 设置单元格宽度
        if (Strings.isNotBlank(cellWidth)) {
            cell.setWidth(cellWidth);
        }
        //=====获取单元格
        CTTc tc = cell.getCTTc();
        //====tcPr开始====》》》》
        CTTcPr tcPr = tc.getTcPr();//获取单元格里的<w:tcPr>
        if (tcPr == null) {//没有<w:tcPr>，创建
            tcPr = tc.addNewTcPr();
        }

        //  --vjc开始-->>
        CTVerticalJc vjc = tcPr.getVAlign();//获取<w:tcPr>  的<w:vAlign w:val="center"/>
        if (vjc == null) {//没有<w:w:vAlign/>，创建
            vjc = tcPr.addNewVAlign();
        }
        //设置单元格对齐方式
        vjc.setVal(vertical.equals("top") ? STVerticalJc.TOP : vertical.equals("bottom") ? STVerticalJc.BOTTOM : STVerticalJc.CENTER); //垂直对齐

        CTShd shd = tcPr.getShd();//获取<w:tcPr>里的<w:shd w:val="clear" w:color="auto" w:fill="C00000"/>
        if (shd == null) {//没有<w:shd>，创建
            shd = tcPr.addNewShd();
        }
        // 设置背景颜色
        shd.setFill(bgColor.substring(1));
        //《《《《====tcPr结束====

        //====p开始====》》》》
        CTP p = tc.getPList().get(0);//获取单元格里的<w:p w:rsidR="00C36068" w:rsidRPr="00B705A0" w:rsidRDefault="00C36068" w:rsidP="00C36068">

        //---ppr开始--->>>
        CTPPr ppr = p.getPPr();//获取<w:p>里的<w:pPr>
        if (ppr == null) {//没有<w:pPr>，创建
            ppr = p.addNewPPr();
        }
        //  --jc开始-->>
        CTJc jc = ppr.getJc();//获取<w:pPr>里的<w:jc w:val="left"/>
        if (jc == null) {//没有<w:jc/>，创建
            jc = ppr.addNewJc();
        }
        //设置单元格对齐方式
        jc.setVal(alignment.equals("left") ? STJc.LEFT : alignment.equals("right") ? STJc.RIGHT : STJc.CENTER); //水平对齐
        //  <<--jc结束--
        //  --pRpr开始-->>
        CTParaRPr pRpr = ppr.getRPr(); //获取<w:pPr>里的<w:rPr>
        if (pRpr == null) {//没有<w:rPr>，创建
            pRpr = ppr.addNewRPr();
        }
        CTFonts pfont = pRpr.getRFonts();//获取<w:rPr>里的<w:rFonts w:ascii="宋体" w:eastAsia="宋体" w:hAnsi="宋体"/>
        if (pfont == null) {//没有<w:rPr>，创建
            pfont = pRpr.addNewRFonts();
        }
        //设置字体
        pfont.setAscii(fontName);
        pfont.setEastAsia(fontName);
        pfont.setHAnsi(fontName);

        CTOnOff pb = pRpr.getB();//获取<w:rPr>里的<w:b/>
        if (pb == null) {//没有<w:b/>，创建
            pb = pRpr.addNewB();
        }
        //设置字体是否加粗
        pb.setVal(fontBold == 1 ? STOnOff.ON : STOnOff.OFF);

        CTHpsMeasure psz = pRpr.getSz();//获取<w:rPr>里的<w:sz w:val="32"/>
        if (psz == null) {//没有<w:sz w:val="32"/>，创建
            psz = pRpr.addNewSz();
        }
        // 设置单元格字体大小
        psz.setVal(bFontSize);
        CTHpsMeasure pszCs = pRpr.getSzCs();//获取<w:rPr>里的<w:szCs w:val="32"/>
        if (pszCs == null) {//没有<w:szCs w:val="32"/>，创建
            pszCs = pRpr.addNewSzCs();
        }
        // 设置单元格字体大小
        pszCs.setVal(bFontSize);
        //  <<--pRpr结束--
        //<<<---ppr结束---

        //---r开始--->>>
        List<CTR> rlist = p.getRList(); //获取<w:p>里的<w:r w:rsidRPr="00B705A0">
        CTR r = null;
        if (rlist != null && rlist.size() > 0) {//获取第一个<w:r>
            r = rlist.get(0);
        } else {//没有<w:r>，创建
            r = p.addNewR();
        }
        //--rpr开始-->>
        CTRPr rpr = r.getRPr();//获取<w:r w:rsidRPr="00B705A0">里的<w:rPr>
        if (rpr == null) {//没有<w:rPr>，创建
            rpr = r.addNewRPr();
        }
        //->-
        CTFonts font = rpr.getRFonts();//获取<w:rPr>里的<w:rFonts w:ascii="宋体" w:eastAsia="宋体" w:hAnsi="宋体" w:hint="eastAsia"/>
        if (font == null) {//没有<w:rFonts>，创建
            font = rpr.addNewRFonts();
        }
        //设置字体
        font.setAscii(fontName);
        font.setEastAsia(fontName);
        font.setHAnsi(fontName);

        CTOnOff b = rpr.getB();//获取<w:rPr>里的<w:b/>
        if (b == null) {//没有<w:b/>，创建
            b = rpr.addNewB();
        }
        //设置字体是否加粗
        b.setVal(fontBold == 1 ? STOnOff.ON : STOnOff.OFF);
        CTColor color = rpr.getColor();//获取<w:rPr>里的<w:color w:val="FFFFFF" w:themeColor="background1"/>
        if (color == null) {//没有<w:color>，创建
            color = rpr.addNewColor();
        }
        // 设置字体颜色
        if (content.contains("↓")) {
            color.setVal("43CD80");
        } else if (content.contains("↑")) {
            color.setVal("943634");
        } else {
            color.setVal(fontColor.substring(1));
        }
        CTHpsMeasure sz = rpr.getSz();
        if (sz == null) {
            sz = rpr.addNewSz();
        }
        sz.setVal(bFontSize);
        CTHpsMeasure szCs = rpr.getSzCs();
        if (szCs == null) {
            szCs = rpr.addNewSz();
        }
        szCs.setVal(bFontSize);
        //-<-
        //<<--rpr结束--
        List<CTText> tlist = r.getTList();
        CTText t = null;
        if (tlist != null && tlist.size() > 0) {//获取第一个<w:r>
            t = tlist.get(0);
        } else {//没有<w:r>，创建
            t = r.addNewT();
        }
        t.setStringValue(content);
        //<<<---r结束---
    }
}
