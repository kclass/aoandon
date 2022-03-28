package com.kclass.aoandon.ui;

import com.kclass.aoandon.constant.Constant;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hu.kai
 * @description 首页
 * @employeeId 10102
 * @date 2022/3/28 10:26
 */
public class MainPage extends JPanel {
    /**
     * 布局管理器
     */
    private CardLayout cardLayout;

    /**
     * 用于判断页面名称是否重复
     */
    private Set<String> pageNames = new HashSet<>();

    /**
     * 头
     */
    private MainHeader mainHeader;

    /**
     * 下部内容
     */
    private JComponent bodyComponent;

    private static MainPage mainPage = new MainPage();

    private MainPage() {
        init();
    }

    public static MainPage getInstance() {
        return mainPage;
    }

    private void init() {
        super.setDoubleBuffered(true);
        this.setBackground(Constant.BG_COLOR);
        this.setLayout(new BorderLayout());


        this.cardLayout = new CardLayout();
        this.bodyComponent = new JPanel(cardLayout);
        this.add(this.bodyComponent, BorderLayout.CENTER);
        //添加头
        this.mainHeader = new MainHeader() {
            @Override
            public void onSelect(String pageName) {
                cardLayout.show(bodyComponent, pageName);
            }
        };
        this.add(this.mainHeader, BorderLayout.NORTH);
    }

    /**
     * 添加页面
     *
     * @param page     页面
     * @param pageName 页面名称
     */
    public void addPage(JComponent page, String pageName) {
        if (page == null) {
            throw new RuntimeException("页面不能为空");
        }
        if (pageName == null) {
            throw new RuntimeException("页面名称不能为空");
        }
        if (!pageNames.add(pageName)) {
            throw new RuntimeException("页面名称重复：" + pageName);
        }
        mainHeader.addPage(pageName);
        bodyComponent.add(page, pageName);
    }
}
