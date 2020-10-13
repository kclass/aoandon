package com.hk.test;

import com.hk.aoandon.util.TextReadListener;

import java.util.Map;

/**
 * @author kai.hu
 * @date 2020/10/9 18:08
 */
public class TestTextReadListener extends TextReadListener {
    public TestTextReadListener(int headLine) {
        super(headLine);
    }

    @Override
    public void parseHeadData(Map<Integer, String> headData) {
        headData.forEach((k,v) -> System.out.println(v));
        System.out.println();
    }

    @Override
    public void parseLineData(Map<Integer, String> lineData) {
        lineData.forEach((k,v) -> {
            System.out.println(v);
        });
        System.out.println();
    }

    @Override
    public void doAfterAllAnalysed() {

    }

    @Override
    public void onException(Exception e) {

    }
}
