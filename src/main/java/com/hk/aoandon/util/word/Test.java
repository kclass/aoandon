package com.hk.aoandon.util.word;

import com.hk.aoandon.util.word.chart.WordChart;
import com.hk.aoandon.util.word.chart.WordChartValue;
import com.hk.aoandon.util.word.enums.TableModelEnum;
import com.hk.aoandon.util.word.enums.WordTypeEnum;
import com.hk.aoandon.util.word.other.BaseOtherReplacer;
import com.hk.aoandon.util.word.picture.WordImage;
import com.hk.aoandon.util.word.table.WordTable;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.util.*;

/**
 * @author kai.hu
 * @date 2021/5/19 11:20
 */
public class Test {

    private static final double width = 15;
    private static final double height = 10;
    public static String[] BAR_COLOR = new String[]{"#ffffff", "#7FFFD4", "#FFDEAD", "#9AFF9A", "#40E0D0", "#ADFF2F"};

    public static void main(String[] args) throws Exception {
        String templateUrl = "template/单站效果评估报告.docx";
        String docPath = "C:\\Users\\hukai\\Desktop\\test\\result.docx";
        Map<String, Object> map = new HashMap<>(32);
        //封装数据
        map.put("enodebName", "我是基站名称");
        map.put("createTime", "2021-05-19");
        map.put("taskName", "集团2021年5月规划");
        map.put("planName", "我是规划站名称");
        map.put("planArea", "四川-成都-青羊区");
        map.put("planNet", "5G");
        map.put("planScene", "1:1共建站");
        map.put("planFreq", "3.5G");
        map.put("planTime", "2020-12-12");
        map.put("inNetTime", "2021-05-19");
        map.put("reportTime", "2021-05-19");
        map.put("stationLocation", "四川-成都-青羊区");
        //站点工参 stationParamTable
        map.put("stationParamTable", getStationParamTable());
        //基站照片 fullScenePic surroundShootingPic cellPic
        map.put("fullScenePic", getFullScenePic(null));
        List<WordImage> surroundShootingPic = new ArrayList<>();
        surroundShootingPic.add(getFullScenePic("0°方向："));
        surroundShootingPic.add(getFullScenePic("90°方向："));
        surroundShootingPic.add(getFullScenePic("270°方向："));
        surroundShootingPic.add(getFullScenePic("180°方向："));
        map.put("surroundShootingPic", surroundShootingPic);

        //单站评估 singleCoverStatistic singleCoverChart
        map.put("singleCoverChart",getChart());

        //基站遗留问题
        map.put("question", null);


        PoiWord poiWord = new PoiWord().defaultRegister();
        BaseOtherReplacer otherReplacer = BaseOtherReplacer.getInstance();
        otherReplacer.replacers.add((doc, paragraph, run, data, key) -> {
            run.setText("我是基站遗留问题", 0);
            return true;
        });
        poiWord.registerReplacer(WordTypeEnum.OTHER,otherReplacer);
        poiWord.exportToWord(docPath, templateUrl, map);
        WordUtil.doc2pdf(docPath,"C:\\Users\\hukai\\Desktop\\test\\result.pdf");
    }

    private static WordChart getChart() {
        WordChart<Double> chart = new WordChart<>();

        chart.setTitle(null);
        chart.setWidth(width);
        chart.setXAxisData(new String[]{"2021/7/30","2021/7/31","2021/8/1","2021/8/2","2021/8/3","2021/8/4","2021/8/5","2021/8/6","2021/8/7","2021/8/8"});

        List<WordChartValue<Double>> yData = new ArrayList<>();

        yData.add(getWordChartValue("TZBCS0420提督街恒大广场写字楼_1",ChartTypes.BAR, null,new Double[]{99.17,98.2,99.1,97.88,null,98.41,99.94,98.42,98.55,99.38}));
        yData.add(getWordChartValue("TZBCS0420提督街恒大广场写字楼_2",ChartTypes.BAR, null,new Double[]{97.69,98.68,98.69,98.77,99.5,98.4,98.4,99.04,98.2,99.57}));
        yData.add(getWordChartValue("TZBCS0420提督街恒大广场写字楼_3",ChartTypes.BAR, null,new Double[]{98.92,97.76,99.82,97.78,98.97,98.14,99.06,99.47,98.03,99.29}));
        yData.add(getWordChartValue("TZBCS0420提督街恒大广场写字楼",ChartTypes.LINE, null,new Double[]{98.59,98.21,99.2,98.14,99.05,null,99.13,98.98,98.26,99.41}));

        chart.setYAxisDataList(yData);
        chart.setHeight(height);
        return chart;
    }

    private static <T extends Number> WordChartValue<T> getWordChartValue(String seriesName, ChartTypes type, String color, T[] data) {
        WordChartValue<T> chartValue = new WordChartValue<>();
        chartValue.setChartType(type);
        chartValue.setSeriesColor(color);
        chartValue.setYAxisData(data);
        chartValue.setSeriesName(seriesName);
        return chartValue;
    }

    private static WordTable getStationParamTable() {
        List<List<String>> result = new ArrayList<>();

        //表头
        result.add(Arrays.asList("规则站逻辑ID", "基站ID", "小区ID", "基站名称", "小区名称", "经度", "维度", "方位角", "机械下倾角", "电子下倾角"));

        //数据
        for (int i = 0; i < 5; i++) {
            List<String> data = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                data.add(Math.random() * 10000 + "");
            }
            result.add(data);
        }

        WordTable table = new WordTable();
        table.setData(result);
        table.setTypeEnum(TableModelEnum.GAP);

        return table;
    }

    private static WordImage getFullScenePic(String title) {
        WordImage img = new WordImage();
        img.setWidth(width);
        img.setHeight(height);
        img.setFilePath("C:\\Users\\hukai\\Desktop\\test\\图片1.png");
        img.setTitle(title);
        return img;
    }

}
