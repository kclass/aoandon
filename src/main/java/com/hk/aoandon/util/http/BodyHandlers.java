package com.hk.aoandon.util.http;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;

/**
 * @author kai.hu
 * @date 2020/10/20 9:53
 */
public abstract class BodyHandlers {

    private static BodyHandler<InputStream> ofInputStream() {
        return (request, http) -> {
            InputStream inputStream = http.getResponseCode() < 400 ? http.getInputStream() : http.getErrorStream();
            // 获取响应头是否有Content-Encoding=gzip
            String gzip = http.getHeaderField(Constant.CONTENT_ENCODING);
            if (Util.isNotEmpty(gzip) && gzip.contains(Constant.GZIP)) {
                inputStream = new GZIPInputStream(inputStream);
            }
            return inputStream;
        };
    }

    public static BodyHandler<Void> ofCallbackByteArray(CallbackByteArray callback) {
        return (request, http) -> {
            try (InputStream inputStream = ofInputStream().accept(request, http)) {
                byte[] bytes = new byte[1024 * 3];
                for (int i; (i = inputStream.read(bytes)) > -1; ) {
                    callback.accept(bytes, 0, i);
                }
                return null;
            }
        };
    }

    public static BodyHandler<byte[]> ofByteArray() {
        return (request, http) -> {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                ofCallbackByteArray((data, index, length) -> {
                    outputStream.write(data, index, length);
                    outputStream.flush();
                }).accept(request, http);
                return outputStream.toByteArray();
            }
        };
    }

    public static BodyHandler<String> ofString(Charset... charset) {
        return (request, http) -> {
            byte[] body = ofByteArray().accept(request, http);
            Charset currentCharset = charset != null && charset.length > 0 ? charset[0] : Constant.defaultCharset;
            return new String(body, currentCharset);
        };
    }

    public static BodyHandler<Path> ofFile(Path path) {
        return (request, http) -> {
            try (OutputStream outputStream = new FileOutputStream(path.toFile())) {
                ofCallbackByteArray(outputStream::write).accept(request, http);
                return path;
            }
        };
    }
}
