package com.kclass.aoandon;

import com.kclass.aoandon.vo.PointInfo;

import java.awt.*;

import static com.kclass.aoandon.util.AoandonUtil.*;

/**
 * @author hu.kai
 * @description xxx
 * @employeeId 10102
 * @date 2022/3/24 10:00
 */
public class App {
    public static void main(String[] args) {
        PointInfo pointInfo = pointInfo();
        System.out.println(pointInfo);

        ROBOT.delay(3000);

        Point point = pointInfo.getPoint();
        mouseMove(point);
    }
}
