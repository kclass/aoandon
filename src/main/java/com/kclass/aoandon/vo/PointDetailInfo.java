package com.kclass.aoandon.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hu.kai
 * @description 页面按钮详情
 * @employeeId 10102
 * @date 2022/3/24 15:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PointDetailInfo extends PointInfo {
    /**
     * 名称，要有唯一性
     */
    private String name;

    /**
     * 详细描述
     */
    private String desc;

    /**
     * 分组，多组用&&隔开
     */
    private String group;

    @Override
    public String toString() {
        return this.name;
    }
}
