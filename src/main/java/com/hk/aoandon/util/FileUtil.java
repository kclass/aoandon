package com.hk.aoandon.util;

import java.io.File;

/**
 * @author kai.hu
 * @date 2021/3/25 17:17
 */
public class FileUtil {
    /**
     * 遍历文件夹下的所有子文件
     *
     * @param fileDo      如果是文件要做的事，如果对象为null,就什么都不做
     * @param directoryDo 如果是文件夹要做的是，如果对象为空，就什么都不做
     * @param file        待遍历的文件
     * @return 返回是否结束遍历
     */
    public static boolean listFile(IFileDo fileDo, IDirectoryDo directoryDo, File file) throws Exception {
        if (file.isFile()) {
            if (fileDo != null) {
                return fileDo.doWithFile(file);
            }
        } else {
            if (directoryDo != null) {
                if (directoryDo.doWithDirectory(file)) {
                    return true;
                }
            }

            File[] files = file.listFiles();
            if (files != null) {
                //子文件进入递归
                for (File fi : files) {
                    if (listFile(fileDo, directoryDo, fi)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 文件处理辅助接口
     */
    @FunctionalInterface
    public interface IFileDo {
        /**
         * 如果是文件要做的事情，调用者自己实现
         *
         * @param file 文件对象
         * @return 是否完成遍历，如果返回true，将结束遍历
         * @throws Exception 可能发生IO等异常，由调用者处理
         */
        boolean doWithFile(File file) throws Exception;
    }

    /**
     * 文件夹处理辅助接口
     */
    @FunctionalInterface
    public interface IDirectoryDo {
        /**
         * 如果是文件夹要做的事情，调用者自己实现
         *
         * @param file 文件夹对象
         * @return 是否完成遍历，如果返回true，将结束遍历
         * @throws Exception 可能发生IO等异常，由调用者处理
         */
        boolean doWithDirectory(File file) throws Exception;
    }
}
