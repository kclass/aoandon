package com.hk.test.music;

import lombok.Data;

/**
 * @author kai.hu
 * @date 2021/5/15 17:19
 */
@Data
public class WebPage {
    public WebPage(String url, PageType pageType) {
        this.url = url;
        this.type = pageType;
    }

    public WebPage(String url, PageType pageType,String html) {
        this.url = url;
        this.type = pageType;
        this.html = html;
    }

    public enum PageType {
        song, playlist, playlists;
    }

    public enum Status {
        crawled, uncrawl;
    }

    private String url;
    private String title;
    private PageType type;
    private Status status;
    private String html;
}
