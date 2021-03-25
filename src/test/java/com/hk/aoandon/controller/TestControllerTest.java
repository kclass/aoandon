package com.hk.aoandon.controller;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.dtsw.core.common.BaseExportModel;
import com.dtsw.core.util.ExcelUtil;
import lombok.Data;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kai.hu
 * @date 2020/12/29 10:22
 */
public class TestControllerTest {
    private String modelName = "com.hk.aoandon.controller.TestModel";

    @Test
    public void test1() throws NoSuchMethodException, InvalidFormatException, InstantiationException, IOException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        ExcelUtil.exportExcel(null, "导出文件", modelName, createData());
    }

    private List<Map<String, Object>> createData() {
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("username", "用户" + i);
            data.put("province", "四川" + i);
            data.put("city", "成都" + i);
            result.add(data);
        }
        return result;
    }
}


@Data
@HeadRowHeight(20)
@ColumnWidth(20)
class TestModel extends BaseExportModel {
    @ExcelProperty(value = {"用户名（平台账号）"})
    private String username;
    @ExcelProperty(value = {"所属省"})
    private String province;
    @ExcelProperty(value = {"所属市"})
    private String city;
}
