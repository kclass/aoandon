package com.hk.aoandon.util;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author kai.hu
 * @date 2020/10/9 17:41
 */
public class BaseUtil {

    /**
     * 获取文件编码格式
     *
     * @param data 文件
     * @return 编码格式
     */
    public static String getFileCharset(byte[] data) {
        String encoding;
        CharsetDetector detector = new CharsetDetector();
        detector.setText(data);
        CharsetMatch match = detector.detect();
        if (match == null) {
            encoding = Charset.defaultCharset().displayName();
        } else {
            encoding = match.getName();
        }
        return encoding;
    }

    /**
     * 获取文件编码格式
     *
     * @param filePath 文件路径
     * @return 编码格式
     * @throws IOException io异常
     */
    public static String getFileCharset(String filePath) throws IOException {
        return getFileCharset(Files.readAllBytes(Paths.get(filePath)));
    }

    /**
     * 获取文件编码格式
     *
     * @param inputStream 文件
     * @return 编码格式
     * @throws IOException io异常
     */
    public static String getFileCharset(InputStream inputStream) throws IOException {
        return getFileCharset(inputStream2Bytes(inputStream));
    }

    /**
     * 输入流转字节数组
     *
     * @param inputStream 输入流
     * @return 字节数组
     * @throws IOException io异常
     */
    public static byte[] inputStream2Bytes(InputStream inputStream) throws IOException {
        return copyInputStream(inputStream).toByteArray();
    }

    /**
     * 将输入流转成字节输出流已到达复用
     *
     * @param inputStream 输入流
     * @return 字节数组
     * @throws IOException io异常
     */
    public static ByteArrayOutputStream copyInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n;
        while (-1 != (n = inputStream.read(buffer))) {
            output.write(buffer, 0, n);
        }
        output.flush();
        return output;
    }
}
