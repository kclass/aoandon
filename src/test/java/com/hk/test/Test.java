package com.hk.test;

/**
 * @author kai.hu
 * @date 2020/10/9 18:07
 */
public class Test {
    public static void main(String[] args) {
        TestTextReadListener listener = new TestTextReadListener(2);
        listener.parseCsvData("D:\\test\\新建文本文档.txt");
    }
}
