package com.hk.aoandon.vo;

import lombok.Data;

/**
 * @author kai.hu
 * @date 2020/11/12 9:14
 */
@Data
public class StationQuestionVo {
    /**
     * 问题标题
     */
    private String questionTitle;
    /**
     * 问题分类
     */
    private String questionSort;
    /**
     * 是否解决
     */
    private String solveFlag;
    /**
     * 问题描述
     */
    private CommentVo questionDescription;
    /**
     * 解决方案
     */
    private CommentVo solution;
    /**
     * 结果
     */
    private CommentVo result;
}
