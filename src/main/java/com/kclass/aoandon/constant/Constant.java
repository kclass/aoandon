package com.kclass.aoandon.constant;

import java.awt.*;

/**
 * @author hu.kai
 * @description 常量
 * @employeeId 10102
 * @date 2022/3/24 10:01
 */
public class Constant {
    /**
     * java工具包
     */
    public static final Toolkit TOOLKIT;

    /**
     * 屏幕高度
     */
    public static final double WINDOW_HEIGHT;

    /**
     * 屏幕宽度
     */
    public static final double WINDOW_WIDTH;

    static {
        TOOLKIT = Toolkit.getDefaultToolkit();
        Dimension screenSize = TOOLKIT.getScreenSize();
        WINDOW_HEIGHT = screenSize.getHeight();
        WINDOW_WIDTH = screenSize.getWidth();
    }
}
