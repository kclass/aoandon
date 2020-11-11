package com.hk.aoandon.util.word;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author kai.hu
 * @date 2020/11/9 9:55
 */
@Data
public class WordChart {
    private String chartTitle;

    private List<String> title;

    private List<String> filedName;

    private List<Map<String, String>> listItem;
}
