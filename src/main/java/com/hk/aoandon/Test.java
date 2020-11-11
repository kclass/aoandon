package com.hk.aoandon;

import cn.afterturn.easypoi.entity.ImageEntity;
import com.hk.aoandon.util.JfreeUtil;
import com.hk.aoandon.util.WordUtil;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kai.hu
 * @date 2020/11/5 16:27
 */
public class Test {
    public static void main(String[] args) throws IOException, InvalidFormatException {
//        HashMap<String, Object> map = new HashMap<>(4);
//
//        //模拟饼状图数据
////        HashMap<String, Integer> datas = new HashMap<>(3);
////        datas.put("一号",10);
////        datas.put("二号",20);
////        datas.put("三号",40);
////        ImageEntity imageEntity = JfreeUtil.pieChart("测试",datas, 500, 300);
////        map.put("picture", imageEntity);
//
//        //模拟其它普通数据
//        map.put("enodebName", "TZBCS0420提督街恒大广场写字楼");
//        map.put("taskName", "2021年第1批次全国滚动规划");
//        map.put("planName", "提督街恒大广场写字楼");
//        map.put("planArea", "四川省 - 成都市 - 青羊区");
//        map.put("planNet", "4G");
//        map.put("planScene", "集团规划场景 - 覆盖类 - 弱覆盖");
//        map.put("planFreq", "800M");
//        map.put("planTime", "2021年2月2日");
//        map.put("inNetTime", "2021年7月7日");
//        map.put("reportTime", "2021年8月8日");
//        map.put("stationLocation", "提督街恒大广场写字楼1号楼32层楼顶");
//
//        //模拟表格数据
//        List<Map<String, String>> list = createStation(12);
//        map.put("stationList", list);
//
//        //基站全景图
//        List<Map<String, Object>> fullScenePictures = createFullScenePic();
//        map.put("fullScenePictures", fullScenePictures);
//
//        WordUtil.exportWord("template/单站效果评估报告.docx", "D:\\plan_test", "单站效果评估报告", map, WordUtil.FILE_TYPE_PDF);
        WordUtil.doc2pdf("D:\\result.docx","D:\\result.pdf");
    }

    public static List<Map<String, Object>> createFullScenePic() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> temp = new HashMap<>(2);
        temp.put("name", "0°方向");
        ImageEntity img = new ImageEntity("D:\\plan_test\\1604644059.png", 500, 500);
        temp.put("pic", img);
        list.add(temp);
        return list;

    }

    public static List<Map<String, String>> createStation(int num) {
        List<Map<String, String>> list = new ArrayList<>(num);
        for (int i = 1; i <= num; i++) {
            Map<String, String> temp = new HashMap<>(16);
            temp.put("planStationId","规划站" + i);
            temp.put("enodebId","基站id:"+i);
            temp.put("cellId","小区id"+i);
            temp.put("enodebName","基站"+i);
            temp.put("cellName","小区"+i);
            temp.put("lon","120"+i);
            temp.put("lat","20"+i);
            temp.put("azimuth","30"+i);
            temp.put("d1","40"+i);
            temp.put("d2","50"+i);
            list.add(temp);
        }
        return list;
    }
}
