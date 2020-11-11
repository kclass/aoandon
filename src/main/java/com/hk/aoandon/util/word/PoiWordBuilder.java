package com.hk.aoandon.util.word;

/**
 * @author kai.hu
 * @date 2020/11/7 9:50
 */
public class PoiWordBuilder {
    public PoiWord deFaultBuild() {
        PoiWordRegister register = new PoiWordRegister();
        return build(register);
    }

    public PoiWord build(PoiWordRegister register) {
        return new PoiWord(register.getWordReplacers(), register.getTableReplacers(), register.getChartReplacers());
    }
}
