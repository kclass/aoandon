package com.kclass.aoandon;

import com.kclass.aoandon.constant.PointDetailInfos;
import com.kclass.aoandon.ui.PointSetPage;
import com.kclass.aoandon.constant.Constant;

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
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        JButton button1 = new JButton("返回");
        button1.addActionListener(e -> {
            cardLayout.show(mainPanel, "2");
        });

        mainPanel.add(button1, "1");
        JButton button = new JButton("123212223221321321421421521");
        button.addActionListener(e -> {
            cardLayout.show(mainPanel, "1");
        });
        mainPanel.add(button, "2");


        PointSetPage pointSetPage = new PointSetPage(PointDetailInfos.pointListMap) {
            @Override
            public void backPage() {
                cardLayout.show(mainPanel, "1");
            }
        };
        mainPanel.add(pointSetPage, "pointSetPage");

        cardLayout.show(mainPanel, "pointSetPage");

        jFrame.add(mainPanel);
    }

    public static void main(String[] args) {
        new App();
    }
}
