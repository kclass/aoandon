package com.hk.aoandon.util.http;

import lombok.ToString;

/**
 * @author kai.hu
 * @date 2020/10/20 9:50
 */
@ToString
public abstract class Param {

    /**
     * 请求头 内容类型
     */
    String contentType;
    /**
     * 请求内容
     */
    byte[] body;

    Param() {
    }

    Param(String contentType, byte[] body) {
        this.contentType = contentType;
        this.body = body;
    }

    /**
     * 获取 param 之前会先调用 ok() 方法, 确保准备完毕
     *
     * @return 参数对象
     */
    public abstract Param ok();
}
