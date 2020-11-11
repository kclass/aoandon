package com.hk.aoandon.util.word;

import lombok.Data;

import java.io.InputStream;

/**
 * @author kai.hu
 * @date 2020/11/6 17:47
 */
@Data
public class WordImage {
    private String filePath;

    private InputStream imageData;

    private int width;

    private int height;

    private String title;
}
