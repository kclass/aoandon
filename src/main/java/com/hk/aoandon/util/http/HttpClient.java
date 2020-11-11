package com.hk.aoandon.util.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.zip.GZIPInputStream;

/*
使用方法:
HttpClient client = HttpClient.buildHttpClient();
Request request = client.buildRequest("http://localhost:8881/charge/testGet?key1=0111&value=shaines.cn");
// Request request = client.buildRequest("http://localhost:8881/charge/testPost").setMethod(Method.POST);
// Request request = client.buildRequest("http://localhost:8881/charge/testPut").setMethod(Method.PUT);
// Request request = client.buildRequest("http://localhost:8881/charge/testFile").setMethod(Method.POST);
request.setParam(Params.ofFormData().add("key1", "1111").add("key3", "2222")
                         .addFile("key2", new File("C:\\Users\\houyu\\Desktop\\1.txt"))
                         .addFile("key4", new File("C:\\Users\\houyu\\Desktop\\2.png")));
Response<String> response = request.execute(BodyHandlers.ofString());
System.out.println("response.getUrl() = " + response.getUrl());
System.out.println("response.getBody() = " + response.getBody());
 */

/**
 * http 客户端
 * @author for.houyu@qq.com
 * @createTime 2019/10/11 22:31
 */
public class HttpClient {

    /**
     * 域对象
     */
    private Session session;

    private HttpClient() {
    }

    public static HttpClient buildHttpClient() {
        HttpClient client = new HttpClient();
        client.session = new Session();
        return client;
    }

    public Request buildRequest(String url) {
        return new Request(url, this);
    }

    public <T> Response<T> execute(Request request, BodyHandler<T> bodyHandler) {
        return Executor.build(request).execute(bodyHandler);
    }

    public Session session() {
        return this.session;
    }

    @Override
    public String toString() {
        return "HttpClient{" + "session=" + this.session +
                '}';
    }


    // =============================================== Executor (执行对象) ======================================================= //

    /**
     * 执行对象
     */

    // =============================================== Request (请求对象) ======================================================== //

    /**
     * 请求对象
     */

    // =============================================== Param (请求参数) ========================================================== //

    /**
     * 请求参数
     */

    // =============================================== Params (请求参数工具) ===================================================== //

    /**
     * 请求参数工具
     */

    // =============================================== Response (响应对象) ======================================================= //

    /**
     * 响应对象
     *
     * @param <T> 响应体类型
     */

    // =============================================== BodyHandler (响应处理接口) ================================================ //

    /**
     * 响应处理接口
     *
     * @param <T> 响应体类型
     */

    // =============================================== CallbackByteArray (回调 byte[] 接口) ===================================== //

    /**
     * 回调 byte[] 接口
     */

    // =============================================== BodyHandlers (响应处理工具) =============================================== //

    /**
     * 响应处理工具
     */

    // =============================================== Method (请求方法) ======================================================== //

    /**
     * 请求方法
     */

    // =============================================== Proxy (代理对象) ========================================================= //

    /**
     * 代理对象
     */

    // =============================================== Constant (常量) ========================================================== //

    /**
     * 常量
     */

    // =============================================== Util (工具类) ============================================================ //

    /**
     * 工具类
     */

}
