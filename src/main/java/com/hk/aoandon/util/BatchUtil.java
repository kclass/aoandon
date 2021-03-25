package com.hk.aoandon.util;

import org.springframework.util.Assert;

import java.util.List;

/**
 * @author kai.hu
 * @date 2019/12/11 13:10
 * <p>
 * 由于postgresql数据库一次只能传递2-bites也就是32767个参数，
 * 需要分批操作
 */
public class BatchUtil {
    /**
     * 默认分批长度
     */
    private static int batchLength = 500;

    /**
     * 分批逻辑
     *
     * @param batchDo 分批后要做的事
     * @param list    待分批数据
     * @param <T>     数据泛型
     */
    public static <T> void batchList(IBatchDo<T> batchDo, List<T> list) throws Exception {
        batchList(batchDo, list, batchLength);
    }

    /**
     * 分批逻辑
     *
     * @param batchDo     分批后要做的事
     * @param list        待分批数据
     * @param batchLength 分批长度
     * @param <T>         数据泛型
     */
    public static <T> void batchList(IBatchDo<T> batchDo, List<T> list, int batchLength) throws Exception {
        Assert.notNull(batchDo, "BatchDo cannot be null");
        if (list == null || list.size() == 0) {
            return;
        }
        int size = list.size();
        int start = 0;
        while (size > batchLength) {
            batchDo.batchDo(list.subList(start, start + batchLength));
            start += batchLength;
            size -= batchLength;
        }
        if (size > 0) {
            batchDo.batchDo(list.subList(start, start + size));
        }
    }

    @FunctionalInterface
    public interface IBatchDo<T> {
        /**
         * 要做的事
         *
         * @param list 分批后的数据
         */
        void batchDo(List<T> list) throws Exception;
    }
}
