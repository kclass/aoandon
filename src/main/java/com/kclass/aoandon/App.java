package com.kclass.aoandon;

import com.kclass.aoandon.constant.Constant;
import com.kclass.aoandon.constant.PointDetailInfos;
import com.kclass.aoandon.ui.MainPage;
import com.kclass.aoandon.ui.PointSetPage;

import javax.swing.*;
import java.awt.*;

/**
 * @author hu.kai
 * @description xxx
 * @employeeId 10102
 * @date 2022/3/24 10:00
 */
public class App {
    private static JFrame jFrame;


    private App() {
        init();
        jFrame.setVisible(Boolean.TRUE);
    }

    private void init() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        jFrame = new JFrame("Aoandon");
        jFrame.setBackground(Color.GREEN);
        jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(App.class.getResource("/icon/aoandon.jpeg")));
        jFrame.setBounds((int) (Constant.WINDOW_WIDTH * (0.5)), (int) (Constant.WINDOW_HEIGHT * (0.5)), (int) (Constant.WINDOW_WIDTH * (3D / 8)), (int) (Constant.WINDOW_HEIGHT * (3D / 8)));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //添加内容
        addContent();
    }

    /**
     * 添加内容
     */
    private void addContent() {
        MainPage mainPage = MainPage.getInstance();
        jFrame.add(mainPage);

        PointSetPage pointSetPage = new PointSetPage(PointDetailInfos.pointListMap) {
            @Override
            public void backPage() {

            }
        };

        mainPage.addPage(pointSetPage, "点位配置1");

        mainPage.addPage(new JLabel("点位配置2"), "点位配置2");
        mainPage.addPage(new JLabel("点位配置3"), "点位配置3");
        mainPage.addPage(new JLabel("点位配置4"), "点位配置4");
        mainPage.addPage(new JLabel("点位配置5"), "点位配置5");
        mainPage.addPage(new JLabel("点位配置6"), "点位配置6");
        mainPage.addPage(new JLabel("点位配置7"), "点位配置7");
    }

    public static void main(String[] args) {
        new App();
    }
}
