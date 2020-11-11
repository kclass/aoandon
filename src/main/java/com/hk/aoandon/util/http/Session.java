package com.hk.aoandon.util.http;

import lombok.Data;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kai.hu
 * @date 2020/10/20 9:42
 */
@Data
@Accessors(chain = true)
public class Session {
    private static final Logger log = LoggerFactory.getLogger(Session.class);

    /**
     * 请求方法
     */
    private final Method method = Method.GET;
    /**
     * 是否编码URL
     */
    private volatile boolean ifEncodeUrl = false;
    /**
     * 是否缓存
     */
    private volatile boolean ifCache = false;
    /**
     * 超时时间 (单位:毫秒) 1分钟
     */
    private volatile int timeout = 60000;
    /**
     * 是否稳定重定向
     */
    private volatile boolean ifStableRedirection = true;
    /**
     * 是否处理https
     */
    private volatile boolean ifHandleHttps = true;
    /**
     * 是否启用默认主机名验证程序
     */
    private volatile boolean ifEnableDefaultHostnameVerifier = false;
    /**
     * 推荐(上一个网页地址)
     */
    private volatile String referer;
    /**
     * cookie
     */
    private final Map<String, String> cookie = new ConcurrentHashMap<>(16);
    /**
     * 代理
     */
    private volatile Proxy proxy;
    /**
     * 参数编码
     */
    private volatile Charset charset = StandardCharsets.UTF_8;
    /**
     * 主机名验证程序
     */
    private HostnameVerifier hostnameVerifier;
    /**
     * SocketFactory
     */
    private SSLSocketFactory sslSocketFactory;
    /**
     * 携带参数(可使用于响应之后的操作)
     */
    private final Map<String, Object> extra = new ConcurrentHashMap<>(16);
    /**
     * 请求头信息 (默认的请求头信息)
     */
    private final Map<String, Object> header = new ConcurrentHashMap<>(8);

    /* -------------------------------- constructor -------------------------- start */

    public Session() {
        this.header.put("Accept", "text/html,application/xhtml+xml,application/xml,application/json;q=0.9,*/*;q=0.8");
        // "Mozilla/5.0 (Linux; Android 8.1.0; 1809-A01 Build/OPM1; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/62.0.3202.97 Mobile Safari/537.36"
        this.header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        this.header.put("Accept-Encoding", "gzip");
        this.header.put("Accept-Language", "zh-CN,zh;q=0.9,zh-TW;q=0.8,en-US;q=0.7,en;q=0.6");
        // this.header.put("Content-Type", "application/x-www-form-urlencoded");
        // this.header = Collections.unmodifiableMap(header);
        // 初始化全局主机名验证程序
        this.hostnameVerifier = (s, sslSession) -> true;
        // 初始化全局主机名验证程序
        final X509TrustManager x509TrustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                // return new X509Certificate[0];
                return null;
            }
        };
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());
            this.sslSocketFactory = sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.warn("init SSLContext has exception ", e);
        }
    }

    public Session setTimeout(int timeout) {
        if (timeout < 0) {
            this.timeout = timeout;
        }
        return this;
    }


    public Session setReferer(String referer) {
        if (referer != null) {
            this.referer = referer;
        }
        return this;
    }

    public Session addCookie(Map<String, String> cookie) {
        if (Util.isNotEmpty(cookie)) {
            this.cookie.putAll(cookie);
        }
        return this;
    }

    /**
     * 覆盖之前的所有 cookie
     */
    public Session setCookie(String cookie) {
        if (Util.isNotEmpty(cookie)) {
            String[] split = cookie.split(Constant.COOKIE_SPLIT);
            for (String cookieObject : split) {
                String[] keyAndVal = cookieObject.split(Constant.EQU, 2);
                this.cookie.put(keyAndVal[0], keyAndVal[1]);
            }
        }
        return this;
    }

    public Session setProxy(Proxy proxy) {
        if (proxy != null) {
            this.proxy = proxy;
        }
        return this;
    }

    public Session setCharset(Charset charset) {
        if (charset != null) {
            this.charset = charset;
        }
        return this;
    }

    public Session setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = Util.nullOfDefault(hostnameVerifier, this.hostnameVerifier);
        return this;
    }

    public Session setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = Util.nullOfDefault(sslSocketFactory, this.sslSocketFactory);
        return this;
    }

    public Session addExtra(Map<String, Object> extra) {
        if (Util.isNotEmpty(extra)) {
            this.extra.putAll(extra);
        }
        return this;
    }

    public Session addExtra(String key, Object val) {
        if (Util.isNotEmpty(key) && Util.isNotEmpty(val)) {
            this.extra.put(key, val);
        }
        return this;
    }

    /**
     * 覆盖之前的所有 extra
     *
     * @param extra 如果不为 null ,那就覆盖之前所有的 extra
     */
    public Session setExtra(Map<String, Object> extra) {
        if (Objects.nonNull(extra)) {
            this.extra.clear();
            this.extra.putAll(extra);
        }
        return this;
    }

    public Session addHeader(Map<String, Object> header) {
        if (Util.isNotEmpty(header)) {
            this.header.putAll(header);
        }
        return this;
    }

    public Session addHeader(String key, Object val) {
        if (Util.isNotEmpty(key) && Util.isNotEmpty(val)) {
            this.header.put(key, val);
        }
        return this;
    }

    /**
     * 覆盖之前的所有 header
     *
     * @param header 如果不为 null ,那就覆盖之前所有的 header
     */
    public Session setHeader(Map<String, Object> header) {
        if (Objects.nonNull(header)) {
            this.header.clear();
            this.header.putAll(header);
        }
        return this;
    }

    public String getCookie() {
        // cookie ex:key2=val2; key1=val1
        StringBuilder builder = new StringBuilder(128);
        for (Map.Entry<String, String> entry : this.cookie.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("; ");
        }
        return builder.length() > 0 ? builder.delete(builder.length() - 2, builder.length()).toString() : "";
    }

    public Proxy getProxy() {
        return this.proxy == null ? null : Proxy.of(this.proxy.getHost(), this.proxy.getPort(), this.proxy.getUsername(), this.proxy.getPassword());
    }

    public Charset getCharset() {
        return Charset.forName(this.charset.name());
    }
}
