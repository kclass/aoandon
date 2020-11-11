package com.hk.aoandon.util.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kai.hu
 * @date 2020/10/20 9:55
 */
public abstract class Util {

    /**
     * 判断对象是否为空
     *
     * @param o 对象
     * @return 是否为空
     */
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof String) {
            return ((String) o).isEmpty();
        } else if (o instanceof Collection) {
            return ((Collection) o).isEmpty();
        } else if (o instanceof Map) {
            return ((Map) o).isEmpty();
        } else if (o instanceof Object[]) {
            return ((Object[]) o).length == 0;
        } else {
            return false;
        }
    }

    /**
     * 判断对象是否不为空
     *
     * @param o 对象
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static <T> T emptyOfDefault(T t, T defaultValue) {
        return isEmpty(t) ? defaultValue : t;
    }

    public static <T> T nullOfDefault(T t, T defaultValue) {
        return t == null ? defaultValue : t;
    }

    /**
     * url 编码
     */
    public static String urlEncode(String text, Charset charset) {
        if (isNotEmpty(text) && isNotEmpty(charset)) {
            // 不为空 并且charset可用
            try {
                return URLEncoder.encode(text, charset.name());
            } catch (UnsupportedEncodingException e) {
                // do non thing
            }
        }
        return text;
    }

    /**
     * @description Map => key1=val1&key2=val2
     * @date 2019-08-20 20:42:59
     * @author houyu for.houyu@foxmail.com
     */
    public static String paramMapAsString(Map<String, Object> paramMap, Charset charset) {
        if (isNotEmpty(paramMap)) {
            StringBuilder builder = new StringBuilder(128);
            paramMap.forEach((k, v) -> {
                // urlEncode : if charset is empty not do Encode
                builder.append(urlEncode(k, charset));
                builder.append(Constant.EQU);
                builder.append(urlEncode(String.valueOf(v), charset));
                builder.append(Constant.AND_SIGN);
            });
            return builder.delete(builder.length() - 1, builder.length()).toString();
        }
        return "";
    }

    /**
     * 把浏览器的Form字符串转为Map
     */
    public static Map<String, Object> parseFormStringAsMap(String s) {
        String[] split = s.split("\n");
        Map<String, Object> targetMap = new HashMap<>(split.length);
        for (String keyAndVal : split) {
            String[] keyVal = keyAndVal.split(": ", 2);
            targetMap.put(keyVal[0], keyVal[1]);
        }
        return targetMap;
    }

    /**
     * 获取错误的详细信息
     */
    @SuppressWarnings("Duplicates")
    public static String getThrowableStackTrace(Throwable e) {
        try (ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream()) {
            e.printStackTrace(new PrintWriter(arrayOutputStream, true));
            return arrayOutputStream.toString();
        } catch (IOException e1) {
            e1.printStackTrace();
            return "";
        }
    }

}
