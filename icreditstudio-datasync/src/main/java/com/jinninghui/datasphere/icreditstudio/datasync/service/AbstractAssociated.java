package com.jinninghui.datasphere.icreditstudio.datasync.service;

import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.Correlations;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Peng
 */
public abstract class AbstractAssociated implements Associated {
    public AbstractAssociated() {
        register();
    }

    @Override
    public void register() {
        Map<String, List<AssocType>> assoc = Correlations.getInstance().getAssoc();
        Map<String, List<String>> conditions = Correlations.getInstance().getConditions();
        if (assoc.containsKey(this.getDialect())) {
            assoc.get(this.getDialect()).addAll(this.getAssocTypes());
        } else {
            assoc.put(this.getDialect(), Lists.newArrayList(this.getAssocTypes()));
        }
        if (conditions.containsKey(this.getDialect())) {
            conditions.get(this.getDialect()).addAll(this.getAssocConditions());
        } else {
            conditions.put(this.getDialect(), Lists.newArrayList(this.getAssocConditions()));
        }
    }

    @Data
    @AllArgsConstructor
    public static class AssocType {
        private Integer code;
        private String assocType;
    }
}
