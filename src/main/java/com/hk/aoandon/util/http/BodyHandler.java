package com.hk.aoandon.util.http;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * @author kai.hu
 * @date 2020/10/20 9:52
 */
public interface BodyHandler<T> {

    /**
     * 回调接口
     *
     * @param request Request 对象
     * @param http    HttpURLConnection 对象
     * @return 返回实例
     * @throws IOException io异常
     */
    T accept(Request request, HttpURLConnection http) throws IOException;
}
