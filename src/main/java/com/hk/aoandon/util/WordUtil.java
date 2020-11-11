package com.hk.aoandon.util;

import cn.afterturn.easypoi.word.WordExportUtil;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.util.Assert;

import java.io.*;
import java.util.Map;

/**
 * @author kai.hu
 * @date 2020/11/5 16:26
 */
public class WordUtil {
    public static final String FILE_TYPE_WORD = ".docx";
    public static final String FILE_TYPE_PDF = ".pdf";

    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = new FileInputStream("template/license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean doc2pdf(String inPath, String outPath) {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return false;
        }
        FileOutputStream os = null;
        try {
            long old = System.currentTimeMillis();
            File file = new File(outPath); // 新建一个空白pdf文档
            os = new FileOutputStream(file);
            Document doc = new Document(inPath); // Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            // EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            System.out.println("pdf转换成功，共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }


    /**
     * 导出word
     * <p>第一步生成替换后的word文件，只支持docx</p>
     * <p>第二步下载生成的文件</p>
     * <p>第三步删除生成的临时文件</p>
     * 模版变量中变量格式：{{foo}}
     *
     * @param templatePath word模板地址
     * @param temDir       生成临时文件存放地址
     * @param fileName     文件名
     * @param params       替换的参数
     * @param fileType      文件类型 pfd/docx
     */
    public static void exportWord(String templatePath, String temDir, String fileName, Map<String, Object> params, String fileType) throws IOException {
        Assert.notNull(templatePath, "模板路径不能为空");
        Assert.notNull(temDir, "临时文件路径不能为空");
        Assert.notNull(fileName, "导出文件名不能为空");
        Assert.isTrue(FILE_TYPE_WORD.equals(fileType) || FILE_TYPE_PDF.equals(fileType), "只支持.docx或.pdf");
        if (!temDir.endsWith("/")) {
            temDir = temDir + File.separator;
        }
        File dir = new File(temDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            XWPFDocument doc = WordExportUtil.exportWord07(templatePath, params);
            String tmpPath = temDir + fileName + FILE_TYPE_WORD;
            fos = new FileOutputStream(tmpPath);
            doc.write(fos);
            fos.flush();
//            if (FILE_TYPE_PDF.equals(fileType)) {
//                wordToPdf(doc, temDir + fileName + FILE_TYPE_PDF);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    public static void wordToPdf(XWPFDocument doc, String filePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        PdfOptions pdfOptions = PdfOptions.create();
        pdfOptions.fontProvider((familyName, s1, v, style, color) -> {
            try {
                BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                Font fontChinese = new Font(bfChinese, 12, style, color);
                if (familyName != null) {
                    fontChinese.setFamily(familyName);
                }
                return fontChinese;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
        PdfConverter.getInstance().convert(doc,fos,pdfOptions);
        fos.close();
    }
}
