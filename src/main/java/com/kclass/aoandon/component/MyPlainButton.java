package com.kclass.aoandon.component;

import com.kclass.aoandon.constant.Constant;

import javax.swing.*;
import java.awt.*;

/**
 * @author hu.kai
 * @description xxx
 * @employeeId 10102
 * @date 2022/3/24 14:34
 */
public class MyPlainButton extends JButton {

    public MyPlainButton(String text) {
        super(text);
        super.setFont(Constant.FONT_RADIO);
        super.setForeground(Color.BLACK);
        super.setMargin(new Insets(5, 5, 5, 5));
        super.setBackground(Color.WHITE);
        super.setBorderPainted(false);
        super.setFocusPainted(false);
        super.setContentAreaFilled(true);
        super.setFocusable(false);
    }
}
