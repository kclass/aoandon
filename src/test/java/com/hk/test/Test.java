package com.hk.test;

import com.google.common.base.Strings;
import com.hk.aoandon.util.http.BodyHandlers;
import com.hk.aoandon.util.http.HttpClient;
import com.hk.aoandon.util.http.Request;
import com.hk.aoandon.util.http.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author kai.hu
 * @date 2020/10/9 18:07
 */
@Slf4j
public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        HttpClient httpClient = HttpClient.buildHttpClient();
        String urlTemplate = "https://www.zikao365.com/shiti/downlist_search.shtm?page=${page}&KeyWord=${KeyWord}";
        String url;
        Scanner scanner = new Scanner(System.in);
        String keyword = null;
        while (Strings.isNullOrEmpty(keyword)) {
            System.out.print("请输入关键字：");
            keyword = scanner.next();
        }

        String filePath = "D:\\test\\" + keyword;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        for (int i = 1; i < 2; i++) {
            // 第一页开始
            url = urlTemplate.replace("${page}", i + "");
            url = url.replace("${KeyWord}", URLEncoder.encode(keyword, "gbk"));
            // 这里我竟然直接使用 GET 也是可以的 ( 我当时是忘记了抓包是 POST ,直接写了GET, 可以,那就更加方便了)
            Request request = httpClient.buildRequest(url).GET();
            Response<String> response = request.execute(BodyHandlers.ofString(Charset.forName("gbk")));
            log.debug("response: [{}]", response.getBody());
            // 把html解析为一个Document
            Document document = Jsoup.parse(response.getBody());
            // 获取每一页的试题超链接
            Elements a = document.select(".bot.clearfix li > a");
            List<String> hrefs = a.stream().map(v -> v.attr("href")).collect(Collectors.toList());
            for (String href : hrefs) {
                // 这里相当于点击进入具体的试题超链接
                response = httpClient.buildRequest(href).execute(BodyHandlers.ofString(Charset.forName("gbk")));
                String html = response.getBody();
                // 解析每一个试题内部的document
                document = Jsoup.parse(html);
                // 获取当前试题的标题
                String title = document.selectFirst(".main div > h1").text();
                // 手动字符串切割获取文件的path
                String[] scripts = StringUtils.substringsBetween(html, "<script", "</script>");
                String path = Arrays.stream(scripts).filter(v -> v.contains("addClick") && v.contains("path")).findFirst().get();
                path = path.substring(path.indexOf("path") + 4);
                path = path.replaceAll("'", "\"");// 这里是为了统一 ' 转为 " , 避免个别页面使用的是' 如: var path = 'http://download.zikao365.com/shiti/19158.pdf'
                path = path.substring(path.indexOf("\"") + 1);
                path = path.substring(0, path.indexOf("\""));
                // 获取文件的后缀
                String suffix = path.substring(path.lastIndexOf("."));
                log.info("title:[{}], path: [{}], suffix: [{}]", title, path, suffix);
                // 执行下载文件到本地目录
                // 也可以使用 commons.io 的工具类下载, 如: FileUtils.copyURLToFile(new URL(path), Paths.get("C:\\Users\\houyu\\Desktop\\马克思主义", title + suffix).toFile());
                httpClient.buildRequest(path).execute(BodyHandlers.ofFile(Paths.get(filePath, title + suffix)));
            }
        }
    }
}

