package com.hk.aoandon.util.http;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author kai.hu
 * @date 2020/10/20 9:44
 */
@Getter
public class Executor {
    private static final Logger log = LoggerFactory.getLogger(Executor.class);

    /**
     * 请求对象
     */
    private Request request;
    /**
     * 重定向的url列表
     */
    private List<String> redirectUrlList;
    /**
     * HttpURLConnection对象
     */
    private HttpURLConnection http;

    public Executor(Request request) {
        this.request = request;
    }

    public static Executor build(Request request) {
        return new Executor(request);
    }

    public <T> Response<T> execute(BodyHandler<T> handler) {
        try {
            return handleHttpConnection(handler);
        } catch (Throwable e) {
            log.warn("handleHttpConnection has exception, use error response. message info: {}", e.getMessage());
            return Response.getErrorResponse(this.request, e);
        }
    }

    private <T> Response<T> handleHttpConnection(BodyHandler<T> handler) {
        // 处理URL参数问题
        this.handleUrlParam();
        // 初始化连接
        this.initConnection();
        // 发送数据包裹
        this.send();
        // 处理重定向
        boolean ifRedirect = this.handleRedirect();
        if (ifRedirect) {
            // 递归实现重定向
            return this.handleHttpConnection(handler);
        }
        // 返回响应
        return new Response<>(this, handler);
    }

    private boolean handleRedirect() {
        if (this.request.isIfStableRedirection()) {
            // 采用稳定重定向方式, 需要处理重定向问题
            int responseCode;
            try {
                responseCode = this.http.getResponseCode();
            } catch (IOException var3) {
                throw new RuntimeException(String.format("%s get response code has exception", this.request.getUrl()), var3);
            }
            if (Constant.REDIRECT_CODES.contains(responseCode)) {
                String redirectURL = this.http.getHeaderField(Constant.LOCATION);
                try {
                    redirectURL = processRedirectURL(redirectURL);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(String.format("%s processRedirectURL has exception", this.request.getUrl()), e);
                }
                this.request.setUrl(redirectURL);
                this.redirectUrlList = Util.nullOfDefault(this.redirectUrlList, new ArrayList<String>(8));
                this.redirectUrlList.add(this.request.getUrl());
                if (this.redirectUrlList.size() < 8) {
                    // 断开本次连接, 然后重新请求
                    this.http.disconnect();
                    log.debug("{} request redirecting ", this.request.getUrl());
                    return true;
                }
            }
        } else {
            // 使用默认的重定向规则处理, 无序手动处理, 但是有可能出现重定向失败
            // do non thing
        }
        return false;
    }

    private String processRedirectURL(String redirectURL) throws MalformedURLException {
        URL previousURL = new URL(this.request.getUrl());
        if (redirectURL.startsWith("/")) {
            // 重定向的URL 是一个绝对路径 https://www.baodu.com/test        https://www.baidu.com:10086/test
            StringBuilder builder = new StringBuilder(previousURL.getProtocol());
            builder.append("://");
            builder.append(previousURL.getHost());
            builder.append(previousURL.getPort() == -1 ? "" : (":" + previousURL.getPort()));
            builder.append(redirectURL);
            redirectURL = builder.toString();
            // } else if(redirectURL.startsWith("./")) {
            //     // 重定向的URL 是一个相对路径 TODO 暂时不处理, 后面有需求再弄
        }
        return redirectURL;
    }

    /**
     * 发送数据
     */
    private void send() {
        try {
            if (Method.GET.equals(this.request.getMethod())) {
                this.http.connect();
            } else {
                // POST...
                this.handleContentTypeAndBody();
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("%s send data has exception", this.request.getUrl()), e);
        }
    }

    /**
     * 处理 ContentType 和 传输内容
     */
    private void handleContentTypeAndBody() throws IOException {
        if (!Method.GET.equals(this.request.getMethod())) {
            // non GET
            /* handle ContentType 有可能多个content-type, 大小写不一致的问题 */
            if (this.request.getParam() == null) {
                this.request.setParam(Params.ofForm());
            }
            this.request.removeHeader("content-type");
            Object tempContentType = this.request.getHeader(Constant.CONTENT_TYPE);
            String contentType = tempContentType == null ? this.request.getParam().contentType : String.valueOf(tempContentType);
            this.addAndRefreshHead(Constant.CONTENT_TYPE, contentType);
            /* handle body */
            // 非GET 所有的请求头必须在调用getOutputStream()之前设置好, 这里相当于GET的connect();
            byte[] body = this.request.getParam().ok().body;
            if (Util.isNotEmpty(body)) {
                try (OutputStream outputStream = this.http.getOutputStream()) {
                    // 使用 try-with-resource 方式处理流, 无需手动关闭流操作
                    outputStream.write(body);
                    outputStream.flush();
                }
            }
        }
    }

    /**
     * 刷新 请求头信息
     */
    private void addAndRefreshHead(String key, Object value) {
        if (Util.isNotEmpty(key) && Util.isNotEmpty(value)) {
            this.request.addHeader(key, value);
            this.http.setRequestProperty(key, String.valueOf(value));
        }
    }

    /**
     * 初始化连接
     */
    private void initConnection() throws RuntimeException {
        URL url;
        try {
            url = new URL(this.request.getUrl());
        } catch (MalformedURLException e) {
            throw new RuntimeException(String.format("%s create URL has exception", this.request.getUrl()), e);
        }
        //
        try {
            this.http = this.openConnection(url, this.request.getProxy());
            //
            if (this.request.getTimeout() > 0) {
                // 设置超时
                this.http.setConnectTimeout(this.request.getTimeout());
                this.http.setReadTimeout(this.request.getTimeout());
            }
            // 设置请求方法
            this.http.setRequestMethod(this.request.getMethod().name());
        } catch (IOException e) {
            throw new RuntimeException(String.format("%s open connection has exception", this.request.getUrl()), e);
        }
        //
        this.http.setDoInput(true);
        if (!Method.GET.equals(this.request.getMethod())) {
            // 非GET方法需要设置可输入
            http.setDoOutput(true);
            http.setUseCaches(false);
        }
        // 设置cookie
        this.setCookie();
        // 设置请求头到连接中
        this.request.getHeader().forEach((k, v) -> this.http.setRequestProperty(k, String.valueOf(v)));
        // 设置缓存
        if (this.request.isIfCache() && !Method.GET.equals(this.request.getMethod())) {
            this.http.setUseCaches(true);
        }
        // 设置是否自动重定向
        this.http.setInstanceFollowRedirects(!(this.request.isIfStableRedirection()));
    }

    private void setCookie() {
        if (Util.isNotEmpty(this.request.getCookie())) {
            log.debug("{} set cookie {}", this.request.getUrl(), this.request.getCookie());
            this.request.removeHeader("cookie");
            this.request.addHeader(Constant.REQUEST_COOKIE, this.request.getCookie());
        }
    }

    /**
     * 打开连接
     */
    private HttpURLConnection openConnection(URL url, Proxy proxy) throws IOException {
        URLConnection connection;
        if (this.request.getProxy() == null) {
            connection = url.openConnection();
        } else if (Util.isNotEmpty(proxy.getUsername())) {
            // 设置代理服务器
            java.net.Proxy javaNetProxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(proxy.getHost(), proxy.getPort()));
            connection = url.openConnection(javaNetProxy);
            String authString = proxy.getUsername() + ":" + proxy.getPassword();
            String auth = "Basic " + Base64.getEncoder().encodeToString(authString.getBytes(this.request.getClient().session().getCharset()));
            connection.setRequestProperty(Constant.PROXY_AUTHORIZATION, auth);
            log.debug("{} do proxy server ", this.request.getUrl());
        } else if (Util.isNotEmpty(proxy.getHost())) {
            // 设置代理主机和端口
            java.net.Proxy javaNetProxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(proxy.getHost(), proxy.getPort()));
            connection = url.openConnection(javaNetProxy);
            log.debug("{} do proxy ", this.request.getUrl());
        } else {
            // 不设置代理
            connection = url.openConnection();
        }
        if (this.request.isIfHandleHttps() && connection instanceof HttpsURLConnection) {
            HttpsURLConnection httpsConn = (HttpsURLConnection) connection;
            // 设置主机名验证程序
            if (this.request.isIfEnableDefaultHostnameVerifier()) {
                httpsConn.setHostnameVerifier(this.request.getHostnameVerifier());
            }
            // 设置ssl factory
            httpsConn.setSSLSocketFactory(this.request.getSslSocketFactory());
        }
        return (HttpURLConnection) connection;
    }

    /**
     * 设置 url 参数问题
     */
    private void handleUrlParam() {
        // 处理url中的query进行url编码
        int indexOf;
        if (this.request.isIfEncodeUrl() && (indexOf = this.request.getUrl().indexOf(Constant.queryFlag)) > -1) {
            String query = this.request.getUrl().substring(indexOf);
            query = Util.urlEncode(query, request.getClient().session().getCharset());
            query = query.replace("%3F", "?").replace("%2F", "/").replace("%3A", ":").replace("%3D", "=").replace("%26", "&").replace("%23", "#");
            this.request.setUrl(this.request.getUrl().substring(0, indexOf) + query);
        }
    }

}
