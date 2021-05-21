package com.hk.test.music;

import lombok.Data;

/**
 * @author kai.hu
 * @date 2021/5/15 17:20
 */
@Data
public class Song {
    private String url;
    private String title;
    private Long commentCount;

}
