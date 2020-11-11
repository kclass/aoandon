package com.hk.test.leetcode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author kai.hu
 * @date 2020/10/20 17:26
 */
public class Test {
    private static final Gson GSON = new GsonBuilder().create();

    public static void main(String[] args) throws IOException {
        String str = IOUtils.toString(new ClassPathResource("test.json").getInputStream());
        long start = System.currentTimeMillis();
        JSONObject jsonObject = JSON.parseObject(str);
        jsonObject.forEach((k, v) -> {
            JSONObject jsonObject1 = JSON.parseObject(v.toString());
            for (Map.Entry<String, Object> entry : jsonObject1.entrySet()) {
                System.out.println(k + ":" + entry.getKey() + ":" + entry.getValue());
            }
        });

//        Type type = new TypeToken<Map<String, RoadTestPointVo>>() {
//        }.getType();
//        Map<String, RoadTestPointVo> points = GSON.fromJson(str, type);
//        System.out.println(System.currentTimeMillis() - start);
//        points.forEach((k, v) -> {
//            System.out.println(k + ":u:" + v.getU());
//            System.out.println(k + ":d:" + v.getD());
//            System.out.println(k + ":r:" + v.getR());
//            System.out.println(k + ":s:" + v.getS());
//        });

        System.out.println(System.currentTimeMillis() - start);
    }
}
