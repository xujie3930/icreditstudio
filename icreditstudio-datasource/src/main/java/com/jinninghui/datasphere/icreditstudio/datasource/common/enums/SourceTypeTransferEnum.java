package com.jinninghui.datasphere.icreditstudio.datasource.common.enums;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

/**
 * @author Peng
 */
@AllArgsConstructor
@Getter
public enum SourceTypeTransferEnum {
    RELATIONAL(1, 0, "关系型"),
    SEMI_STRUCTURED(2, 0, "半结构化"),
    NOSQL(3, 0, "NOSQL"),
    LOCAL(4, 1, "本地文件"),
    BLOCKCHAIN(5, 2, "区块链"),
    ;
    private Integer catalogue;
    private Integer type;
    private String name;

    public static SourceTypeTransferEnum find(Integer catalogue) {
        for (SourceTypeTransferEnum value : SourceTypeTransferEnum.values()) {
            if (value.catalogue.equals(catalogue)) {
                return value;
            }
        }
        return null;
    }

    public static Set<Integer> getCatalogue(Integer type) {
        Set<Integer> results = Sets.newHashSet();
        for (SourceTypeTransferEnum value : SourceTypeTransferEnum.values()) {
            if (value.type.equals(type)) {
                results.add(value.getCatalogue());
            }
        }
        return results;
    }
}
