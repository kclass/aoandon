package com.hk.aoandon;

import com.hk.aoandon.util.JfreeUtil;
import com.hk.aoandon.util.word.PoiWordBuilder;
import com.hk.aoandon.util.word.PoiWordRegister;
import com.hk.aoandon.util.word.WordChart;
import com.hk.aoandon.util.word.WordImage;
import lombok.extern.log4j.Log4j2;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author kai.hu
 * @date 2020/11/6 17:13
 */
@Log4j2
public class Test2 {
    public static void main(String[] args) throws Exception {
        // 结果文件
        final String returnUrl = "D:\\result.docx";
        // 模板文件
        final String templateUrl = "template/单站效果评估报告.docx";

        Map<String, Object> map = new HashMap<>(32);
        //模拟其它普通数据
        map.put("enodebName", "TZBCS0420提督街恒大广场写字楼");
        map.put("createTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        map.put("taskName", "2021年第1批次全国滚动规划");
        map.put("planName", "提督街恒大广场写字楼");
        map.put("planArea", "四川省 - 成都市 - 青羊区");
        map.put("planNet", "4G");
        map.put("planScene", "集团规划场景 - 覆盖类 - 弱覆盖");
        map.put("planFreq", "800M");
        map.put("planTime", "2021年2月2日");
        map.put("inNetTime", "2021年7月7日");
        map.put("reportTime", "2021年8月8日");
        map.put("stationLocation", "提督街恒大广场写字楼1号楼32层楼顶");
        //工参
        map.put("stationParamTable", createTable());
        //基站全景图
        List<WordImage> fullScenePictures = createFullScenePic();
        map.put("fullScenePic", fullScenePictures);
        //基站环拍图
        map.put("surroundShootingPic", createFullScenePic());
        //基站小区图
        map.put("cellPic", createFullScenePic());
        //mr图
        map.put("mr1", creatMrImg("mr1"));
        map.put("mr2", creatMrImg("mr2"));
        map.put("mr3", creatMrImg("mr3"));
        map.put("mr4", creatMrImg("mr4"));

        //单站覆盖统计表格
        List<Map<String, Object>> statisticData = createStatisticData(100);
        map.put("singleCoverStatistic", convertStatisticData(statisticData));
        map.put("singleCoverChart", createChartData(statisticData, 100));

        //单站流量统计
        List<Map<String, Object>> statisticData2 = createStatisticData(10000);
        map.put("singleFluxStatistic", convertStatisticData(statisticData2));
        map.put("singleFluxChart", createChartData(statisticData2, 10000));

        //单站用户统计
        List<Map<String, Object>> statisticData3 = createStatisticData(10000000);
        map.put("singleUserStatistic", convertStatisticData(statisticData3));
        map.put("singleUserChart", createChartData(statisticData3, 10000000));


        //区域覆盖统计表格
        map.put("areaCoverStatistic", createAreaData("区域覆盖率(%)", 100));
        map.put("areaCoverChart", createAreaChartData("区域覆盖率(%)", 100));

        //单站流量统计
        map.put("areaFluxStatistic", createAreaData("流量(GB)", 10000));
        map.put("areaFluxChart", createAreaChartData("流量(GB)", 10000));

        //单站用户统计
        map.put("areaUserStatistic", createAreaData("用户数(个)", 10000000));
        map.put("areaUserChart", createAreaChartData("用户数(个)", 10000000));


        PoiWordRegister register = new PoiWordRegister();
        register.registerWordTableReplacer(new GapWordTableReplacer(null));
        new PoiWordBuilder().build(register).exportToWord(returnUrl, templateUrl, map);
    }

    public static WordChart createAreaChartData(String name, int num) {
        List<String> title = new ArrayList<>();
        List<String> filedName = new ArrayList<>();
        List<Map<String, String>> listItem = new ArrayList<>();

        title.add("");
        title.add("建站前-"+name);
        title.add("建站后-"+name);
        filedName.add("c0");
        filedName.add("c1");
        filedName.add("c2");


        for (int i = 0; i < 10; i++) {
            Map<String, String> temp = new HashMap<>(2);
            temp.put("c0", "date" + i);
            temp.put("c1", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            temp.put("c2", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            temp.put("c3", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            listItem.add(temp);
        }

        WordChart chart = new WordChart();
        chart.setTitle(title);
        chart.setFiledName(filedName);
        chart.setListItem(listItem);
        return chart;
    }

    public static List<Map<String, Object>> createAreaData(String name, int num) {
        Map<String, Object> head1 = new LinkedHashMap<>();
        head1.put("c1", "建站前-时间");
        head1.put("c2", "2020-11-1");
        head1.put("c3", "2020-11-2");
        head1.put("c4", "2020-11-3");
        head1.put("c5", "2020-11-4");
        head1.put("c6", "2020-11-5");
        head1.put("c7", "2020-11-6");
        head1.put("c8", "2020-11-7");
        head1.put("c9", "2020-11-8");
        head1.put("c10", "2020-11-9");
        head1.put("c11", "2020-11-10");

        Map<String, Object> content1 = new LinkedHashMap<>();
        content1.put("c1", "建站前-" + name);
        content1.put("c2", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content1.put("c3", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content1.put("c4", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content1.put("c5", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content1.put("c6", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content1.put("c7", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content1.put("c8", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content1.put("c9", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content1.put("c10", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content1.put("c11", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        Map<String, Object> head2 = new LinkedHashMap<>();
        head2.put("c1", "建站后-时间");
        head2.put("c2", "2021-7-30");
        head2.put("c3", "2021-7-31");
        head2.put("c4", "2021-8-1");
        head2.put("c5", "2021-8-1");
        head2.put("c6", "2021-8-1");
        head2.put("c7", "2021-8-1");
        head2.put("c8", "2021-8-1");
        head2.put("c9", "2021-8-1");
        head2.put("c10", "2021-8-1");
        head2.put("c11", "2021-8-1");

        Map<String, Object> content2 = new LinkedHashMap<>();
        content2.put("c1", "建站后-" + name);
        content2.put("c2", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content2.put("c3", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content2.put("c4", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content2.put("c5", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content2.put("c6", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content2.put("c7", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content2.put("c8", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content2.put("c9", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content2.put("c10", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        content2.put("c11", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

        List<Map<String, Object>> result = new ArrayList<>();
        result.add(head1);
        result.add(content1);
        result.add(head2);
        result.add(content2);
        return result;
    }

    public static WordChart createChartData(List<Map<String, Object>> data, int num) {
        List<String> title = new ArrayList<>();
        List<String> filedName = new ArrayList<>();
        List<Map<String, String>> listItem = new ArrayList<>();

        title.add("");
        filedName.add("c0");
        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> da = data.get(i);
            title.add(da.get("cellName").toString());
            filedName.add("c" + (i + 1));
        }

        for (int i = 0; i < 10; i++) {
            Map<String, String> temp = new HashMap<>(2);
            temp.put("c0", "date" + i);
            temp.put("c1", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            temp.put("c2", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            temp.put("c3", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            temp.put("c4", new BigDecimal(Math.random() * num).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            listItem.add(temp);
        }

        WordChart chart = new WordChart();
        chart.setTitle(title);
        chart.setFiledName(filedName);
        chart.setListItem(listItem);

        return chart;
    }

    public static List<Map<String, Object>> convertStatisticData(List<Map<String, Object>> data) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> head = new LinkedHashMap<>();
        head.put("object", "统计对象");
        List<Map<String, Object>> param = (List<Map<String, Object>>) data.get(0).get("data");
        for (int i = 0; i < param.size(); i++) {
            Map<String, Object> p = param.get(i);
            head.put("t" + i, p.get("date"));
        }
        result.add(head);

        data.forEach(da -> {
            List<Map<String, Object>> data1 = (List<Map<String, Object>>) da.get("data");
            Map<String, Object> content = new HashMap<>(data1.size() + 1);
            content.put("object", da.get("cellName"));
            for (int i = 0; i < data1.size(); i++) {
                Map<String, Object> m = data1.get(i);
                content.put("t" + i, m.get("value"));
            }
            result.add(content);
        });

        return result;
    }

    public static List<Map<String, Object>> createStatisticData(int maxNum) {
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Map<String, Object> cell = new HashMap<>(2);
            cell.put("cellName", "TZBCS0420提督街恒大广场写字楼_" + i);
            data.add(cell);
        }

        data.forEach(cell -> {
            List<Map<String, Object>> param = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Map<String, Object> temp = new HashMap<>(2);
                temp.put("date", "date" + i);
                temp.put("value", new BigDecimal(Math.random() * maxNum).setScale(2, BigDecimal.ROUND_HALF_UP));
                param.add(temp);
            }
            cell.put("data", param);
        });

        return data;
    }

    public static WordImage creatMrImg(String name) {
        WordImage image = new WordImage();
        image.setFilePath("D:\\plan_test\\" + name + ".jpg");
        image.setWidth(400);
        image.setHeight(250);
        return image;
    }

    public static List<Map<String, Object>> createTable() {
        List<Map<String, Object>> table = new ArrayList<>();
        Map<String, Object> head = new LinkedHashMap<>();
        for (int i = 0; i < 10; i++) {
            head.put("c" + i, "表头" + i);
        }
        table.add(head);

        for (int i = 0; i < 20; i++) {
            Map<String, Object> data = new HashMap<>(10);
            for (int j = 0; j < 10; j++) {
                data.put("c" + j, "内容" + i + j);
            }
            table.add(data);
        }

        return table;
    }

    public static List<WordImage> createFullScenePic() {
        List<WordImage> list = new ArrayList<>();
        WordImage image = new WordImage();
//        image.setFilePath("D:\\plan_test\\1604644059.png");
        image.setImageData(new ByteArrayInputStream(JfreeUtil.barChart("标题", null, 800, 540).getData()));
        image.setTitle("\t\t我是标题：");
        image.setWidth(400);
        image.setHeight(270);

        list.add(image);
        list.add(image);

        return list;
    }
}
