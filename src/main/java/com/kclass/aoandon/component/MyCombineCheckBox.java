package com.kclass.aoandon.component;

import com.kclass.aoandon.constant.Constant;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author hu.kai
 * @description 组合分组选择器
 * @employeeId 10102
 * @date 2022/3/29 11:03
 */
public class MyCombineCheckBox extends JPanel {
    /**
     * 提示信息
     */
    private String tip;

    /**
     * 选择项
     */
    private Collection<String> checkItems;

    /**
     * 初始选中的点
     */
    private List<String> initSelectedItems;

    /**
     * 复选框组件
     */
    private List<JCheckBox> checkBoxes = new ArrayList<>();

    public MyCombineCheckBox(String tip, Collection<String> checkItems, String... selectItems) {
        super(true);
        this.tip = tip;
        this.checkItems = checkItems;
        this.initSelectedItems = Arrays.asList(selectItems);
        init();
    }

    /**
     * 初始化组件
     */
    private void init() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        this.setBackground(Constant.BG_COLOR);
        if (this.tip != null) {
            JLabel tipLabel = new JLabel(this.tip + ":");
            tipLabel.setFont(Constant.FONT_RADIO);
            this.add(tipLabel);
        }

        for (String checkItem : checkItems) {
            JCheckBox checkBox = new JCheckBox(checkItem, this.initSelectedItems.contains(checkItem));
            checkBox.setBackground(Constant.BG_COLOR);
            this.add(checkBox);
            checkBoxes.add(checkBox);
        }
    }

    /**
     * 获取选中的复选框列表
     *
     * @return 选中的复选框列表
     */
    public List<String> getCheckedItem() {
        List<String> result = new ArrayList<>();
        for (JCheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                result.add(checkBox.getText());
            }
        }

        return result;
    }
}
