package com.kclass.aoandon.ui;

import com.kclass.aoandon.constant.Constant;

import javax.swing.*;
import java.awt.*;

/**
 * @author hu.kai
 * @description xxx
 * @employeeId 10102
 * @date 2022/3/28 9:39
 */
public abstract class MainHeader extends JPanel {
    /**
     * 下拉选择器
     */
    private JComboBox<String> pageComboBox;

    public MainHeader() {
        super(true);
        init();
    }

    private void init() {
        this.setBackground(Constant.BG_COLOR);
        this.setLayout(new BorderLayout());
        JPanel pageSelectPanel = new JPanel();
        pageSelectPanel.setBackground(Constant.BG_COLOR);
        pageSelectPanel.setPreferredSize(new Dimension(100, 40));
        this.add(pageSelectPanel, BorderLayout.EAST);

        this.pageComboBox = new JComboBox<>();
        this.pageComboBox.setBackground(Constant.BG_COLOR);
        this.pageComboBox.setFont(Constant.FONT_RADIO);
        this.pageComboBox.addActionListener(e -> {
            String pageName = (String) this.pageComboBox.getSelectedItem();
            if (pageName != null) {
                this.onSelect(pageName);
            }
        });
        pageSelectPanel.add(this.pageComboBox);
    }

    /**
     * 添加页面选项
     *
     * @param pageName 页面名称
     */
    public void addPage(String pageName) {
        this.pageComboBox.addItem(pageName);
    }

    /**
     * 选中回调
     *
     * @param pageName 选中页面名称
     */
    public abstract void onSelect(String pageName);
}
