package com.hk.aoandon.util;

import cn.afterturn.easypoi.entity.ImageEntity;
import lombok.extern.log4j.Log4j2;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.util.Assert;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * @author kai.hu
 * @date 2020/11/5 16:24
 */
@Log4j2
public class JfreeUtil {

    private static String tempImgPath="D:\\tempJfree.jpeg";

    /**
     * 将图片转化为字节数组
     * @return 字节数组
     */
    private static byte[] imgToByte(){
        File file = new File(tempImgPath);
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        //删除临时文件
        file.delete();
        return buffer;
    }

    public static ImageEntity pieChart(String title, Map<String, Integer> datas, int width, int height) {

        //创建主题样式
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
        //设置标题字体
        standardChartTheme.setExtraLargeFont(new Font("宋体", Font.BOLD, 20));
        //设置图例的字体
        standardChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
        //设置轴向的字体
        standardChartTheme.setLargeFont(new Font("宋体", Font.PLAIN, 15));
        //设置主题样式
        ChartFactory.setChartTheme(standardChartTheme);

        //根据jfree生成一个本地饼状图
        DefaultPieDataset pds = new DefaultPieDataset();
        datas.forEach(pds::setValue);
        //图标标题、数据集合、是否显示图例标识、是否显示tooltips、是否支持超链接
        JFreeChart chart = ChartFactory.createPieChart(title, pds, true, false, false);
        //设置抗锯齿
        chart.setTextAntiAlias(false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setNoDataMessage("暂无数据");
        //忽略无值的分类
        plot.setIgnoreNullValues(true);
        plot.setBackgroundAlpha(0f);
        //设置标签阴影颜色
        plot.setShadowPaint(new Color(255,255,255));
        //设置标签生成器(默认{0})
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1})/{2}"));
        try {
            ChartUtils.saveChartAsJPEG(new File(tempImgPath), chart, width, height);
        } catch (IOException e1) {
            log.error("生成饼状图失败！");
        }
        ImageEntity imageEntity = new ImageEntity(imgToByte(), width, height);
        Assert.notNull(imageEntity.getData(),"生成饼状图对象失败！");
        return imageEntity;
    }

    public static ImageEntity barChart(String title, Map<String, Integer> datas, int width, int height) {

        //图标标题、数据集合、是否显示图例标识、是否显示tooltips、是否支持超链接
        JFreeChart chart = ChartFactory.createBarChart(null, null, null, createDataset2());
        iSetBarChart(chart);
        try {
            ChartUtils.saveChartAsJPEG(new File(tempImgPath), chart, width, height);
        } catch (IOException e1) {
            log.error("生成饼状图失败！");
        }
        ImageEntity imageEntity = new ImageEntity(imgToByte(), width, height);
        Assert.notNull(imageEntity.getData(),"生成柱状图图对象失败！");
        return imageEntity;
    }

    public static void iSetBarChart(JFreeChart chart) {
        CategoryPlot categoryplot = chart.getCategoryPlot();// 图本身
        ValueAxis rangeAxis = categoryplot.getRangeAxis();
        CategoryAxis domainAxis = categoryplot.getDomainAxis();
        // 设置Y轴的提示文字样式
        rangeAxis.setLabelFont(new Font("微软雅黑", Font.PLAIN, 12));
        // 设置Y轴刻度线的长度
        rangeAxis.setTickMarkInsideLength(10f);

        // rangeAxis.setTickMarkOutsideLength(10f);
        // 设置X轴下的标签文字
        domainAxis.setLabelFont(new Font("微软雅黑", Font.PLAIN, 12));
        // 设置X轴上提示文字样式
        domainAxis.setTickLabelFont(new Font("微软雅黑", Font.PLAIN, 12));
        NumberAxis vn = (NumberAxis) categoryplot.getRangeAxis();

        // 设置Y轴的数字为百分比样式显示
//        DecimalFormat df = new DecimalFormat("0.0%");
//        vn.setNumberFormatOverride(df);
        // 使柱状图反过来显示
        // vn.setInverted(true);
        // vn.setVerticalTickLabels(true);

        // 自定义柱状图中柱子的样式
        BarRenderer brender = new BarRenderer();
        brender.setSeriesPaint(1, Color.decode("#C0504D")); // 给series1 Bar
        brender.setSeriesPaint(0, Color.decode("#E46C0A")); // 给series2 Bar
        brender.setSeriesPaint(2, Color.decode("#4F81BD")); // 给series3 Bar
        brender.setSeriesPaint(3, Color.decode("#00B050")); // 给series4 Bar
        brender.setSeriesPaint(4, Color.decode("#7030A0")); // 给series5 Bar
        brender.setSeriesPaint(5, Color.decode("#00BF00")); // 给series6 Bar
        // 设置柱状图的顶端显示数字
        brender.setIncludeBaseInRange(true);
        brender.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        brender.setDefaultItemLabelsVisible(false);
        // 设置柱子为平面图不是立体的
        brender.setBarPainter(new StandardBarPainter());
        // 设置柱状图之间的距离0.1代表10%；
        brender.setItemMargin(0.1);
        // 设置柱子的阴影，false代表没有阴影
        brender.setShadowVisible(false);

        // 设置图的背景为白色
        categoryplot.setBackgroundPaint(Color.WHITE);
        // 设置背景虚线的颜色
        categoryplot.setRangeGridlinePaint(Color.decode("#B6A2DE"));
        // 去掉柱状图的背景边框，使边框不可见
        categoryplot.setOutlineVisible(false);
        // 设置标题的字体样式
//        chart.getTitle().setFont(new Font("微软雅黑", Font.PLAIN, 24));
        // 设置图表下方图例上的字体样式
        chart.getLegend().setItemFont(new Font("微软雅黑", Font.PLAIN, 12));

        categoryplot.setRenderer(brender);

        categoryplot.setDataset(1, createLineDataset());
        LineAndShapeRenderer lineandshaperenderer = new LineAndShapeRenderer();
        lineandshaperenderer.setSeriesPaint(0, Color.decode("#9BBB59"));
        lineandshaperenderer.setDefaultToolTipGenerator(new StandardCategoryToolTipGenerator());
        categoryplot.setRenderer(1, lineandshaperenderer);
        categoryplot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);// 折线在柱面前面显示
    }

    public static CategoryDataset createDataset2() {

        DefaultCategoryDataset result = new DefaultCategoryDataset();

        String series1 = "海南";
        String series2 = "青藏";
        String series3 = "青海";
        String series4 = "上海";
        String series5 = "北京";
        String series6 = "山西";
        String type1 = "城市状况";
        result.addValue(0.1, type1, series1);
        result.addValue(0.2, type1, series2);
        result.addValue(0.3, type1, series3);
        result.addValue(0.4, type1, series4);
        result.addValue(0.5, type1, series5);
        result.addValue(0.7, type1, series6);
        String type2 = "haha";
        result.addValue(0.2, type2, series1);
        result.addValue(0.3, type2, series2);
        result.addValue(0.4, type2, series3);
        result.addValue(0.5, type2, series4);
        result.addValue(0.6, type2, series5);
        result.addValue(0.8, type2, series6);
        return result;

    }

    public static CategoryDataset createLineDataset() {
        DefaultCategoryDataset result = new DefaultCategoryDataset();

        String series1 = "海南";
        String series2 = "青藏";
        String series3 = "青海";
        String series4 = "上海";
        String series5 = "北京";
        String series6 = "山西";
        String type1 = "综合";
        result.addValue(0.1, type1, series1);
        result.addValue(0.2, type1, series2);
        result.addValue(0.3, type1, series3);
        result.addValue(0.4, type1, series4);
        result.addValue(0.5, type1, series5);
        result.addValue(0.7, type1, series6);
        return result;
    }
}
