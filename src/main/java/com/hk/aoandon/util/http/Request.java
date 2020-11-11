package com.hk.aoandon.util.http;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import java.util.Map;

/**
 * @author kai.hu
 * @date 2020/10/20 9:44
 */
@Data
@Accessors(chain = true)
public class Request {

    /**
     * 请求网站地址
     */
    private String url;
    /**
     * 请求方法
     */
    private Method method;
    /**
     * 请求头
     */
    private Map<String, Object> header;
    /**
     * 请求参数
     */
    private Param param;
    /**
     * 携带参数(可使用于响应之后的操作)
     */
    private Map<String, Object> extra;
    /**
     * 代理
     */
    private Proxy proxy;
    /**
     * 是否编码URL
     */
    private boolean ifEncodeUrl;
    /**
     * 是否缓存
     */
    private boolean ifCache;
    /**
     * 连接超时(单位:毫秒)
     */
    private int timeout;
    /**
     * 携带cookie(优先) ex: key1=val1; key2=val2
     */
    private String cookie;
    /**
     * 是否稳定重定向
     */
    private boolean ifStableRedirection;
    /**
     * 是否处理https
     */
    private boolean ifHandleHttps;
    /**
     * 是否启用默认主机名验证程序
     */
    private boolean ifEnableDefaultHostnameVerifier;
    /**
     * 主机名验证程序
     */
    private HostnameVerifier hostnameVerifier;
    /**
     * SocketFactory
     */
    private SSLSocketFactory sslSocketFactory;
    /**
     * 客户端
     */
    private HttpClient client;

    public Request(String url, HttpClient client) {
        this.setUrl(url);
        this.client = client;
        this.init();
    }

    private void init() {
        Session session = client.session();
        this.setMethod(session.getMethod());
        this.setHeader(session.getHeader());
        if (Util.isNotEmpty(session.getReferer())) {
            this.addHeader(Constant.REFERER, session.getReferer());
        }
        this.setExtra(session.getExtra());
        this.setProxy(session.getProxy());
        this.setIfEncodeUrl(session.isIfEncodeUrl());
        this.setIfCache(session.isIfCache());
        this.setTimeout(session.getTimeout());
        this.setCookie(session.getCookie());
        this.setIfStableRedirection(session.isIfStableRedirection());
        this.setIfHandleHttps(session.isIfHandleHttps());
        this.setIfEnableDefaultHostnameVerifier(session.isIfEnableDefaultHostnameVerifier());
        this.setHostnameVerifier(session.getHostnameVerifier());
        this.setSslSocketFactory(session.getSslSocketFactory());
    }

    public <T> Response<T> execute(BodyHandler<T> bodyHandler) {
        return this.client.execute(this, bodyHandler);
    }

    /* ---------------------------- setter ---------------------------------- start */

    public Request setUrl(String url) {
        // 校验url地址
        this.url = String.valueOf(url);
        if (url.startsWith("//")) {
            this.url = "http:" + this.url;
        } else if (url.startsWith("://")) {
            this.url = "http" + this.url;
        } else if (!this.url.toLowerCase().startsWith(Constant.HTTP)) {
            this.url = Constant.HTTP + "://" + this.url;
        }
        return this;
    }

    public Request setMethod(Method method) {
        this.method = Util.nullOfDefault(method, Method.GET);
        return this;
    }

    public Request GET() {
        return this.setMethod(Method.GET);
    }

    public Request POST() {
        return this.setMethod(Method.POST);
    }

    public Request PUT() {
        return this.setMethod(Method.PUT);
    }

    public Request DELETE() {
        return this.setMethod(Method.DELETE);
    }

    /**
     * 调用这个方法会覆盖之前所有的请求头
     *
     * @param header 如果 header 不为 null ,那就覆盖之前的所有 header
     */
    public Request setHeader(Map<String, Object> header) {
        this.header = Util.nullOfDefault(header, this.header);
        return this;
    }

    public Request addHeader(String key, Object val) {
        if (Util.isNotEmpty(key) && Util.isNotEmpty(val)) {
            // 这里 header 不可以能为 null
            this.header.put(key, val);
        }
        return this;
    }

    public Request removeHeader(String key) {
        if (Util.isNotEmpty(key)) {
            // 这里 header 不可以能为 null
            this.header.remove(key);
        }
        return this;
    }

    public Request setParam(Param param) {
        this.param = Util.nullOfDefault(param, this.param);
        return this;
    }

    /**
     * 调用这个方法会覆盖之前所有的extra
     *
     * @param extra 如果 extra 不为 null ,那就覆盖之前的所有 extra
     */
    public Request setExtra(Map<String, Object> extra) {
        this.extra = Util.nullOfDefault(extra, this.extra);
        return this;
    }

    public Request addExtra(String key, Object val) {
        if (Util.isNotEmpty(key) && Util.isNotEmpty(val)) {
            // 这里 extra 不可以能为 null
            this.extra.put(key, val);
        }
        return this;
    }

    public Request setProxy(Proxy proxy) {
        this.proxy = Util.nullOfDefault(proxy, this.proxy);
        return this;
    }

    public Request setTimeout(int timeout) {
        this.timeout = timeout < 0 ? this.timeout : timeout;
        return this;
    }

    public Request setCookie(String cookie) {
        this.cookie = Util.nullOfDefault(cookie, this.cookie);
        return this;
    }

    public Request setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = Util.nullOfDefault(hostnameVerifier, this.hostnameVerifier);
        return this;
    }

    public Request setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = Util.nullOfDefault(sslSocketFactory, this.sslSocketFactory);
        return this;
    }

    /* ---------------------------- setter ---------------------------------- end */

    /* ---------------------------- getter ---------------------------------- start */

    public Object getHeader(String key) {
        return Util.isNotEmpty(key) ? this.header.get(key) : null;
    }
    /* ---------------------------- getter ---------------------------------- end */
}
