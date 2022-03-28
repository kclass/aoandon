package com.kclass.aoandon.constant;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kclass.aoandon.util.AoandonUtil;
import com.kclass.aoandon.vo.PointDetailInfo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hu.kai
 * @description 所有的点信息
 * @employeeId 10102
 * @date 2022/3/24 15:41
 */
public class PointDetailInfos {
    /**
     * 所有的点
     */
    public static Map<String, List<PointDetailInfo>> pointListMap;

    /**
     * 所有的点
     */
    private static List<PointDetailInfo> pointDetailInfos;

    /**
     * 配置文件
     */
    private static File configFile;

    static {
        pointListMap = new LinkedHashMap<>();
        configFile = new File("point.txt");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("点配置文件创建失败，请检查权限");
            }
        } else {
            try {
                String json = AoandonUtil.readAsString(configFile, StandardCharsets.UTF_8);
                Map<String, JSONArray> map = JSONObject.parseObject(json, Map.class);
                map.forEach((k, v) -> {
                    List<PointDetailInfo> points = new ArrayList<>();
                    int size = v.size();
                    for (int i = 0; i < size; i++) {
                        PointDetailInfo object = v.getObject(i, PointDetailInfo.class);
                        points.add(object);
                        pointDetailInfos.add(object);
                    }
                    pointListMap.put(k, points);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加分组
     *
     * @param groupName 分组名称
     */
    public static void addGroup(String groupName) {
        if (pointListMap.containsKey(groupName)) {
            throw new RuntimeException("分组已存在，添加失败");
        }
        pointListMap.put(groupName, new ArrayList<>());
        try {
            Files.write(configFile.toPath(), JSONObject.toJSON(pointListMap).toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("配置文件写入失败");
        }
    }

    /**
     * 修改分组名称
     *
     * @param oldGroupName 旧名称
     * @param newGroupName 新名称
     */
    public static void updateGroup(String oldGroupName, String newGroupName) {
        if (newGroupName == null || newGroupName.isEmpty()) {
            throw new RuntimeException("分组名称不能为空");
        }
        if (newGroupName.equals(oldGroupName)) {
            return;
        }
        List<PointDetailInfo> remove = pointListMap.remove(oldGroupName);
        pointListMap.put(newGroupName, remove);
    }
}
