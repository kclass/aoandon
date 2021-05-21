package com.hk.aoandon.util.word.picture;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.InputStream;

/**
 * @author kai.hu
 * @date 2020/11/6 17:47
 */
@Accessors(chain = true)
@Data
public class WordImage {
    /**
     * 图片文件路径,会优先用输入流
     */
    private String filePath;

    /**
     * 图片文件输入流，会优先用这个
     * 该对象使用了会关闭，请注意
     */
    private InputStream imageIn;

    /**
     * 图片宽度
     */
    private double width;

    /**
     * 图片高度
     */
    private double height;

    /**
     * 图片标题，有值默认会展示在图片上方
     */
    private String title;
}
