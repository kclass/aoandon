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

    /**
     * 屏幕缩放比例
     */
    public static final double WINDOW_SCALE = 1.25D;

    /**
     * 字体
     */
    public final static String FONT_FAMILY = "宋体";
    /**
     * 标题字体
     */
    public final static Font FONT_TITLE = new Font(FONT_FAMILY, Font.BOLD, 27);
    /**
     * 普通字体
     */
    public final static Font FONT_NORMAL = new Font(FONT_FAMILY, Font.PLAIN, 13);
    /**
     * radio字体
     */
    public final static Font FONT_RADIO = new Font(FONT_FAMILY, Font.PLAIN, 15);

    /**
     * 背景颜色
     */
    public final static Color BG_COLOR = Color.WHITE;

    /**
     * 组名分割符
     */
    public final static String GROUP_SPLIT = "&&";

    static {
        TOOLKIT = Toolkit.getDefaultToolkit();
        Dimension screenSize = TOOLKIT.getScreenSize();
        WINDOW_HEIGHT = screenSize.getHeight();
        WINDOW_WIDTH = screenSize.getWidth();
    }
}
