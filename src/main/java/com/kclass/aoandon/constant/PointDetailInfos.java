package com.kclass.aoandon.constant;

import com.kclass.aoandon.vo.PointDetailInfo;

import java.util.ArrayList;
import java.util.HashMap;
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

    static {
        pointListMap = new HashMap<>();
        List<PointDetailInfo> alist = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PointDetailInfo info = new PointDetailInfo();
            info.setName("aaa名称" + i);
            info.setDesc("描述信息" + i);
            alist.add(info);
        }

        List<PointDetailInfo> blist = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PointDetailInfo info = new PointDetailInfo();
            info.setName("bbb名称" + i);
            info.setDesc("描述信息" + i);
            blist.add(info);
        }
        pointListMap.put("aaa", alist);
        pointListMap.put("bbb", blist);
        pointListMap.put("ccc", alist);
        pointListMap.put("ddd", blist);
    }

}
