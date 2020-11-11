package com.hk.aoandon.util.http;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author kai.hu
 * @date 2020/10/20 9:54
 */
public interface Constant {

    String CONTENT_LENGTH = "Content-Length";
    String CONTENT_TYPE = "Content-Type";
    /**
     * 获取响应的COOKIE
     */
    String RESPONSE_COOKIE = "Set-Cookie";
    /**
     * 设置发送的COOKIE
     */
    String REQUEST_COOKIE = "Cookie";
    String REFERER = "Referer";
    String PROXY_AUTHORIZATION = "Proxy-Authorization";
    String CONTENT_ENCODING = "Content-Encoding";
    String LOCATION = "Location";

    String CONTENT_TYPE_WITH_FORM = "application/x-www-form-urlencoded; charset=";
    String CONTENT_TYPE_WITH_FORM_DATA = "multipart/form-data; boundary=";
    String CONTENT_TYPE_WITH_JSON = "application/json; charset=";
    String GZIP = "gzip";

    int REDIRECT_CODE_301 = 301;
    int REDIRECT_CODE_302 = 302;
    int REDIRECT_CODE_303 = 303;
    Set<Integer> REDIRECT_CODES = new HashSet<>(Arrays.asList(REDIRECT_CODE_301, REDIRECT_CODE_302, REDIRECT_CODE_303));

    String COOKIE_SPLIT = "; ";
    String EQU = "=";
    String HTTP = "http";
    String AND_SIGN = "&";
    String queryFlag = "?";

    Charset defaultCharset = Charset.defaultCharset();
}
