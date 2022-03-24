package com.kclass.aoandon.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.awt.*;

/**
 * @author hu.kai
 * @description 点信息，位置和颜色
 * @employeeId 10102
 * @date 2022/3/24 10:56
 */
@Data
@Accessors(chain = true)
public class PointInfo {
    /**
     * 点位置信息
     */
    private Point point;

    /**
     * 颜色
     */
    private Color color;

}
