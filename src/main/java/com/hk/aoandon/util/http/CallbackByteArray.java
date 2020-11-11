package com.hk.aoandon.util.http;

import java.io.IOException;

/**
 * @author kai.hu
 * @date 2020/10/20 9:52
 */
public interface CallbackByteArray {

    /**
     * 回调 byte[] 方法
     *
     * @param data byte[] 对象
     * @param index byte[] 的开始下标( 通常是0 )
     * @param length byte[] 结束下标
     * @throws IOException io异常
     */
    void accept(byte[] data, int index, int length) throws IOException;
}
