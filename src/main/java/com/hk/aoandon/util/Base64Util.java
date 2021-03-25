package com.hk.aoandon.util;

import java.util.Base64;

/**
 * @author kai.hu
 * @date 2020/11/26 16:34
 */
public class Base64Util {
    public static final Base64.Decoder decoder = Base64.getDecoder();
    public static final Base64.Encoder encoder = Base64.getEncoder();

    public static byte[] decode(byte[] key) {
       return decoder.decode(key);
    }

    public static byte[] encode(byte[] key) {
        return encoder.encode(key);
    }
}
