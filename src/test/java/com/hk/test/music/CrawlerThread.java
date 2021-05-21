package com.hk.test.music;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kai.hu
 * @date 2021/5/15 17:21
 */
public class CrawlerThread {
    private static final String BASE_URL = "http://music.163.com";

    private boolean fetchHtml(WebPage webPage) throws IOException {
        Connection.Response response = Jsoup.connect(webPage.getUrl()).timeout(3000).execute();
        webPage.setHtml(response.body());
        return response.statusCode() / 100 == 2;
    }

    private List<WebPage> parsePlaylist(WebPage webPage) {
        Elements songs = Jsoup.parse(webPage.getHtml()).select("ul.m-cvrlst li p a");
        return songs.stream().map(e -> new WebPage(BASE_URL + e.attr("href"), WebPage.PageType.song, e.html())).collect(Collectors.toList());
    }

    public static void main(String[] args) throws Exception {
//        WebPage playlists = new WebPage("http://music.163.com/discover/playlist/?order=hot&cat=%E5%85%A8%E9%83%A8&limit=35&offset=0", WebPage.PageType.playlists);
//        CrawlerThread crawlerThread = new CrawlerThread();
//        crawlerThread.fetchHtml(playlists);
//        List<WebPage> webPages = crawlerThread.parsePlaylist(playlists);
//        webPages.forEach(e-> {
//            System.out.println(e.getUrl());
//        });

//        RestTemplate template = new RestTemplate();
//        String forObject = template.getForObject("http://music.163.com/#/discover/playlist/?order=hot&cat=%E5%85%A8%E9%83%A8&limit=35&offset=0", String.class);
//        System.out.println(forObject);

        System.out.println((char)127);
    }
}
