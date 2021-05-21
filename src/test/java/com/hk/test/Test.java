package com.hk.test;

import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

/**
 * @author kai.hu
 * @date 2021/4/23 17:17
 */
public class Test {
    public static void main(String[] args) {
        RestTemplate template = new RestTemplate();

        String forObject = template.getForObject("http://music.163.com/song?id=512485370&market=baiduqk", String.class);
        System.out.println(forObject);
    }
}
