package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasync.service.AbstractAssociated;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Peng
 */
@Component
public class MysqlAssociated extends AbstractAssociated {

    @Override
    public List<AssocType> getAssocTypes() {
        return Lists.newArrayList(new AssocType(0, "left join"), new AssocType(1, "inner join"));
    }

    @Override
    public List<String> getAssocConditions() {
        return Lists.newArrayList("=", ">", "<");
    }

    @Override
    public String getDialect() {
        return "mysql";
    }
}
