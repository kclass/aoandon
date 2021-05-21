package com.hk.aoandon.vo;

import lombok.Data;

import java.io.InputStream;

/**
 * @author kai.hu
 * @date 2020/11/10 15:41
 */
@Data
public class ImgVo {
    /**
     * 图片标题，如果有默认会呈现
     */
    private String imgTitle;

    /**
     * 图片文件路径，会优先取文件输入流
     */
    private String imgPath;
}
