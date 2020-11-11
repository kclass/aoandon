package com.hk.aoandon.util.http;

import lombok.Data;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kai.hu
 * @date 2020/10/20 9:51
 */
@Data
public class Response<T> {
    /**
     * 执行者
     */
    private Executor executor;
    /**
     * 响应处理
     */
    private BodyHandler<T> bodyHandler;
    /**
     * HttpURLConnection
     */
    private HttpURLConnection http;
    /**
     * 请求url
     */
    private String url;
    /**
     * 重定向的url列表
     */
    private List<String> redirectUrlList;
    /**
     * http响应状态码(HttpURLConnection.HTTP_OK)
     */
    private int statusCode = -1;
    /**
     * 响应头信息
     */
    private Map<String, List<String>> header;
    /**
     * cookie ex:key2=val2; key1=val1
     */
    private Map<String, String> cookie;
    /**
     * 携带参数(可使用于响应之后的操作)
     */
    private HashMap<String, Object> extra;
    /**
     * 响应体
     */
    private T body;
    /**
     * 错误信息
     */
    private String errorMessage;

    private Response() {
    }

    public Response(Executor executor, BodyHandler<T> bodyHandler) {
        this.executor = executor;
        this.bodyHandler = bodyHandler;
        this.init();
    }

    public static <T> Response<T> getErrorResponse(Request request, Throwable e) {
        Response<T> errorResponse = new Response<>();
        errorResponse.http = null;
        errorResponse.url = request.getUrl();
        errorResponse.redirectUrlList = Collections.emptyList();
        errorResponse.statusCode = 400;
        errorResponse.header = Collections.emptyMap();
        errorResponse.cookie = Collections.emptyMap();
        errorResponse.extra = new HashMap<>(request.getExtra());
        errorResponse.body = null;
        errorResponse.errorMessage = Util.getThrowableStackTrace(e);
        return errorResponse;
    }

    private void init() {
        try {
            this.http = this.executor.getHttp();
            this.url = this.executor.getRequest().getUrl();
            this.redirectUrlList = this.executor.getRedirectUrlList();
            this.redirectUrlList = this.redirectUrlList == null ? Collections.emptyList() : this.redirectUrlList;
            this.statusCode = this.executor.getHttp().getResponseCode();
            this.header = this.executor.getHttp().getHeaderFields();
            this.cookie = this.parseCookieAsMap();
            this.extra = new HashMap<>(this.executor.getRequest().getExtra());
            this.handleHttpClientSession();
            if (this.bodyHandler != null) {
                this.body = this.bodyHandler.accept(this.executor.getRequest(), this.http);
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("%s init HttpResponse has exception", this.url), e);
        } finally {
            this.http.disconnect();
        }
    }

    private void handleHttpClientSession() {
        Session session = this.executor.getRequest().getClient().session();
        session.setReferer(this.url);
        session.addCookie(this.cookie);
        session.addExtra(this.extra);
    }

    /**
     * 获取 cookieMap
     */
    private Map<String, String> parseCookieAsMap() {
        List<String> cookieList = this.header.get(Constant.RESPONSE_COOKIE);
        Map<String, String> cookieMap = Collections.emptyMap();
        if (Util.isNotEmpty(cookieList)) {
            cookieMap = new HashMap<>(cookieList.size());
            if (Util.isNotEmpty(cookieList)) {
                for (String cookieObj : cookieList) {
                    String[] split = cookieObj.split(Constant.COOKIE_SPLIT);
                    if (split.length > 0) {
                        String[] keyAndVal = split[0].split(Constant.EQU, 2);
                        cookieMap.put(keyAndVal[0], keyAndVal[1]);
                    }
                }
            }
        }
        return cookieMap;
    }
}
