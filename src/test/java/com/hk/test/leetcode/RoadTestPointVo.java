package com.hk.test.leetcode;

import lombok.Data;

/**
 * 路测点封装类，
 * 这里变量命名简单是因为减少数据库存储的大小
 *
 * @author kai.hu
 * @date 2020/10/22 11:32
 */
@Data
public class RoadTestPointVo {
    /**
     * RSRP
     */
    private Double r;

    /**
     * SINR
     */
    private Double s;

    /**
     *Throughput DL
     */
    private Double d;

    /**
     * Throughput UL
     */
    private Double u;

    /**
     * 路测点经度
     */
    private Double lon;

    /**
     * 路测点纬度
     */
    private Double lat;
}
