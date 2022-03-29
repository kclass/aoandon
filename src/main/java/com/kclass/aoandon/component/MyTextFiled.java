package com.kclass.aoandon.component;

import com.kclass.aoandon.constant.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @author hu.kai
 * @description xxx
 * @employeeId 10102
 * @date 2022/3/29 14:24
 */
public abstract class MyTextFiled extends JPanel {
    /**
     * 文本输入框
     */
    private JTextField txt;

    /**
     * 提示
     */
    private JLabel tipLabel;

    public MyTextFiled(String name, String tip) {
        super(true);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        this.setBackground(Constant.BG_COLOR);
        //文本提示
        JLabel label = new JLabel(name + "：");
        label.setFont(Constant.FONT_RADIO);
        this.add(label);
        //文本输入框
        txt = new JTextField();
        txt.setPreferredSize(new Dimension(150, 25));
        txt.setHorizontalAlignment(JTextField.CENTER);
        txt.setFont(Constant.FONT_RADIO);
        txt.setHorizontalAlignment(SwingConstants.LEFT);
        txt.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        if (tip != null) {
            txt.setText(tip);
        }
        txt.setFont(Constant.FONT_RADIO);
        txt.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                txt.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent e) {
                txt.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                onTextFocusLost();
            }
        });
        this.add(txt);
        //提示
        tipLabel = new JLabel();
        this.add(tipLabel);
    }

    /**
     * 设置文本输入框状态
     *
     * @param isOk 是否ok
     * @param msg  展示信息
     */
    public void setTextFiledStatus(boolean isOk, String msg) {
        txt.setBorder(BorderFactory.createLineBorder(isOk ? Color.GREEN : Color.RED));
        tipLabel.setForeground(isOk ? Color.GREEN : Color.RED);
        tipLabel.setText(msg);
        tipLabel.setFont(Constant.FONT_RADIO);
    }

    /**
     * 文本框失去焦点后
     */
    protected abstract void onTextFocusLost();

    /**
     * 获取输入内容
     *
     * @return 输入内容
     */
    public String getInputTxt() {
        return txt.getText();
    }
}
