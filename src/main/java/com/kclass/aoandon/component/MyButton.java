package com.kclass.aoandon.component;

import javax.swing.*;
import java.awt.*;

/**
 * @author hu.kai
 * @description xxx
 * @employeeId 10102
 * @date 2021/10/19 10:09
 */
public class MyButton extends JButton {

    private long buttonId;

    private ImageIcon iconEnable, iconDisable;

    private String tip;

    public MyButton(ImageIcon normalIcon, ImageIcon iconEnable, ImageIcon iconDisable, String tip) {
        super(normalIcon);
        this.iconDisable = iconDisable;
        this.iconEnable = iconEnable;
        this.tip = tip;
        init();
        setUp();
        buttonId = System.nanoTime();
    }

    private void init() {
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setFocusable(true);
        this.setMargin(new Insets(0, 0, 0, 0));
    }

    private void setUp() {
        this.setRolloverIcon(iconEnable);
        this.setPressedIcon(iconEnable);
        this.setDisabledIcon(iconDisable);
        this.setSelectedIcon(iconEnable);
        this.setToolTipText(tip);
    }

    public long getButtonId() {
        return this.buttonId;
    }

    public void selected(boolean select) {
        this.setIcon(select ? iconEnable : iconDisable);
    }
}
