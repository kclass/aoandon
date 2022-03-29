package com.kclass.aoandon.constant;

import com.alibaba.fastjson.JSONArray;
import com.kclass.aoandon.util.AoandonUtil;
import com.kclass.aoandon.vo.PointDetailInfo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

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
                pointDetailInfos = JSONArray.parseArray(json, PointDetailInfo.class);
                for (PointDetailInfo pointDetailInfo : pointDetailInfos) {
                    String group = pointDetailInfo.getGroup();
                    String[] split = group.split(Constant.GROUP_SPLIT);
                    for (String s : split) {
                        List<PointDetailInfo> pl = pointListMap.get(s);
                        if (pl == null || pl.isEmpty()) {
                            pl = new ArrayList<>();
                            pl.add(pointDetailInfo);
                            pointListMap.put(s, pl);
                        } else {
                            pl.add(pointDetailInfo);
                        }
                    }
                }
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
        for (PointDetailInfo pointDetailInfo : pointDetailInfos) {
            String[] split = pointDetailInfo.getGroup().split(Constant.GROUP_SPLIT);
            for (int i = 0; i < split.length; i++) {
                if (split[i].equals(oldGroupName)) {
                    split[i] = newGroupName;
                    pointDetailInfo.setGroup(AoandonUtil.appendStr(Constant.GROUP_SPLIT, split));
                    writeConfig();
                    break;
                }
            }
        }
    }

    /**
     * 写配置
     */
    private static void writeConfig() {
        try {
            Files.write(configFile.toPath(), JSONArray.toJSON(pointDetailInfos).toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("配置文件写入失败");
        }
    }

    /**
     * 检查点名称是否可用
     *
     * @param pointName 点名称
     * @return 是否可用
     */
    public static boolean checkPointNameValid(String pointName) {
        if (pointName == null || pointName.isEmpty()) {
            return false;
        }
        for (PointDetailInfo pointDetailInfo : pointDetailInfos) {
            if (pointName.equals(pointDetailInfo.getName())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 新增点
     *
     * @param point 点信息
     * @throws Exception 受检异常
     */
    public static void addPoint(PointDetailInfo point) throws Exception {
        String group = point.getGroup();
        if (group == null || group.isEmpty()) {
            throw new RuntimeException("分组至少选择一个");
        }
        if (point.getDesc() == null || point.getDesc().isEmpty()) {
            throw new RuntimeException("描述必填");
        }
        if (Objects.isNull(point.getColor()) || Objects.isNull(point.getPoint())) {
            throw new RuntimeException("点位必填");
        }
        String name = point.getName();
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("点名称必填");
        } else if (!checkPointNameValid(name)) {
            throw new RuntimeException("点名称重复");
        }

        pointDetailInfos.add(point);
        for (String s : group.split(Constant.GROUP_SPLIT)) {
            List<PointDetailInfo> pointDetailInfos = pointListMap.get(s);
            if (Objects.nonNull(pointDetailInfos)) {
                pointDetailInfos.add(point);
            } else {
                pointDetailInfos = new ArrayList<>();
                pointDetailInfos.add(point);
                pointListMap.put(s, pointDetailInfos);
            }
        }
        writeConfig();
    }
}
