package com.hk.aoandon.util.http;

import lombok.Data;

/**
 * @author kai.hu
 * @date 2020/10/20 9:54
 */
@Data
public class Proxy {

    /**
     * 主机
     */
    private String host;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    public Proxy(String host, Integer port) {
        this(host, port, null, null);
    }

    public Proxy(String host, Integer port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public static Proxy of(String host, Integer port) {
        return of(host, port, null, null);
    }

    public static Proxy of(String host, Integer port, String username, String password) {
        return new Proxy(host, port, username, password);
    }
}
