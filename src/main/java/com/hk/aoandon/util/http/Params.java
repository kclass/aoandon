package com.hk.aoandon.util.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author kai.hu
 * @date 2020/10/20 9:51
 */
public abstract class Params {

    /* ---------------------------------- 实现 Param 的内部类 ----------------------------- start */

    public static class ParamJson extends Param {

        ParamJson(String jsonString, Charset charset) {
            super(Constant.CONTENT_TYPE_WITH_JSON + charset.name(), jsonString.getBytes(charset));
        }

        @Override
        public Param ok() {
            return this;
        }
    }

    public static class ParamForm extends Param {

        Map<String, Object> paramMap;
        Charset charset;

        ParamForm(Charset charset) {
            this.charset = charset;
            this.paramMap = new HashMap<>(8);
        }

        public ParamForm add(String key, Object val) {
            if (Util.isNotEmpty(key) && Util.isNotEmpty(val)) {
                this.paramMap.put(key, val);
            }
            return this;
        }

        public ParamForm add(Map<String, Object> paramMap) {
            if (Util.isNotEmpty(paramMap)) {
                paramMap.forEach(this::add);
            }
            return this;
        }

        @Override
        public Param ok() {
            this.contentType = Constant.CONTENT_TYPE_WITH_FORM + this.charset.name();
            this.body = Util.paramMapAsString(this.paramMap, this.charset).getBytes(this.charset);
            return this;
        }
    }

    public static class ParamFormData extends Param {
        @Override
        public Param ok() {
            this.body = this.fillData();
            return this;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder("ParamFormData{").append("\n");
            builder.append("contentType='").append("\n").append(contentType).append('\'').append("\n");
            builder.append("body=").append("\n").append(new String(this.body, this.charset)).append("\n");
            builder.append("charset=").append("\n").append(charset).append("\n");
            builder.append('}');
            return builder.toString();
        }

        /* ------------------------------------------------------------------------------------------- */

        public static class Resource {

            public File file;
            public Charset charset;

            public Resource(File file, Charset charset) {
                this.file = file;
                this.charset = Util.nullOfDefault(charset, Constant.defaultCharset);
            }

            public File getFile() {
                return this.file;
            }

            public Charset getCharset() {
                return this.charset;
            }
        }

        private static final String horizontalLine = "--------------------------";
        private static final String lineFeed = System.lineSeparator();
        private static final String fileFormat = "Content-Disposition: form-data; name=\"%s\"; filename=\"%s\"\nContent-Type: %s";
        private static final String textFormat = "Content-Disposition: form-data; name=\"%s\"";

        Charset charset;
        String separator;
        String endFlag;
        Map<String, Object> tempMap;

        protected ParamFormData(Charset charset) {
            this.charset = charset;
            this.init();
        }

        private void init() {
            long randomNumber = ThreadLocalRandom.current().nextLong();
            contentType = Constant.CONTENT_TYPE_WITH_FORM_DATA + horizontalLine + randomNumber;
            separator = "--" + horizontalLine + randomNumber;
            endFlag = separator + "--" + lineFeed;
            tempMap = new LinkedHashMap<>(8);
        }

        public ParamFormData add(String key, Object val) {
            if (Util.isNotEmpty(key) && Util.isNotEmpty(val)) {
                this.tempMap.put(key, val);
            }
            return this;
        }

        public ParamFormData addFile(String key, File file) {
            return this.addFile(key, file, null);
        }

        public ParamFormData addFile(String key, File file, Charset charset) {
            if (Util.isNotEmpty(key) && file != null) {
                this.add(key, new Resource(file, charset));
            }
            return this;
        }

        private byte[] fillData() {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                for (Map.Entry<String, Object> entry : this.tempMap.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (value instanceof Resource) {
                        this.appendResource(outputStream, key, (Resource) value);
                    } else {
                        this.appendText(outputStream, key, value);
                    }
                }
                outputStream.write(this.endFlag.getBytes(this.charset));
                outputStream.flush();
                return outputStream.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void appendResource(OutputStream outputStream, String key, Resource value) {
            StringBuilder builder = new StringBuilder(1024);
            File file = value.getFile();
            Path path = Paths.get(file.getAbsolutePath());
            try {
                // append 头部信息
                builder.append(separator).append(lineFeed);
                builder.append(String.format(fileFormat, key, file.getName(), this.parseFileType(path))).append(lineFeed);
                builder.append(lineFeed);
                outputStream.write(builder.toString().getBytes(value.getCharset()));
                // append 实体
                Files.copy(path, outputStream);
                // append 换行
                outputStream.write(lineFeed.getBytes(this.charset));
                outputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void appendText(OutputStream outputStream, String key, Object value) {
            StringBuilder builder = new StringBuilder(1024);
            try {
                // append 头部信息
                builder.append(separator).append(lineFeed);
                builder.append(String.format(textFormat, key)).append(lineFeed);
                builder.append(lineFeed);
                // append 实体
                builder.append(value);
                outputStream.write(builder.toString().getBytes(this.charset));
                // append 换行
                outputStream.write(lineFeed.getBytes(this.charset));
                outputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private String parseFileType(Path path) throws IOException {
            return Files.probeContentType(path);
        }
    }

    /* ---------------------------------- 实现 Param 的内部类 ----------------------------- end   */

    /* ---------------------------------- 提供快捷实现静态方法 ----------------------------- start   */

    public static ParamJson ofJson(String jsonString, Charset charset) {
        return new ParamJson(jsonString, charset);
    }

    public static ParamJson ofJson(String jsonString) {
        return ofJson(jsonString, Constant.defaultCharset);
    }

    public static ParamForm ofForm(Charset charset) {
        return new ParamForm(charset);
    }

    public static ParamForm ofForm() {
        return ofForm(Constant.defaultCharset);
    }

    public static ParamFormData ofFormData(Charset charset) {
        return new ParamFormData(charset);
    }

    public static ParamFormData ofFormData() {
        return ofFormData(Constant.defaultCharset);
    }

    /* ---------------------------------- 提供快捷实现静态方法 ----------------------------- end   */

}
