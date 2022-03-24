package com.kclass.aoandon.util;

import com.kclass.aoandon.vo.PointInfo;

import java.awt.*;

import static com.kclass.aoandon.constant.Constant.WINDOW_SCALE;

/**
 * @author hu.kai
 * @description 基础工具包
 * @employeeId 10102
 * @date 2022/3/24 10:14
 */
public class AoandonUtil {
    /**
     * 机器人对象
     */
    public static final Robot ROBOT = AoandonUtil.creatRobot();

    /**
     * 获取点信息
     */
    public static PointInfo pointInfo() {
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        Point location = pointerInfo.getLocation();
        Color color = ROBOT.getPixelColor((int) location.getX(), (int) location.getY());

        return new PointInfo()
                .setColor(color)
                .setPoint(location);
    }

    /**
     * 创建机器人,创建不了就结束程序
     *
     * @return 机器人
     */
    public static Robot creatRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    /**
     * 移动鼠标
     *
     * @param point 点
     */
    public static void mouseMove(Point point) {
        ROBOT.mouseMove(-1, -1);
        ROBOT.mouseMove((int) (point.getX() / WINDOW_SCALE), (int) (point.getY() / WINDOW_SCALE));
    }
}
