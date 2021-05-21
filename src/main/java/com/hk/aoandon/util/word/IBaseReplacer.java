package com.hk.aoandon.util.word;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kai.hu
 * @date 2021/5/19 11:18
 */
public abstract class IBaseReplacer {
    /**
     * 这一类的替换器,
     *
     * 如果要添加，需要往前面添加，默认的替换器不管替换成功与否，都会返回true，导致后面的替换器不会用到
     */
    public List<IReplacer> replacers = new ArrayList<>();

    /**
     * 返回匹配文档中关键字的提取的前部分
     *
     * @return 关键字的前缀
     */
    public abstract String prefix();

    /**
     * 返回匹配文档中关键字的提取的后部分
     *
     * @return 关键字的后缀
     */
    public abstract String suffix();

    public boolean registerReplacer( IReplacer replacer) {
        return replacers.add(replacer);
    }
}
