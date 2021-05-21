package com.hk.aoandon.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author kai.hu
 * @date 2020/11/14 18:03
 */
@Data
@ApiModel("评论加多图片模式")
public class CommentVo {
    @ApiModelProperty("描述")
    private String desc;

    @ApiModelProperty("多图片")
    private List<ImgVo> img;
}
