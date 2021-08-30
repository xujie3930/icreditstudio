package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasync.service.AbstractAssociated;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Peng
 */
@Component
public class MysqlAssociated extends AbstractAssociated {

    @Override
    public List<AssociatedType> getAssocTypes() {
        return Lists.newArrayList(new AssociatedType(0, "left join"), new AssociatedType(1, "inner join"));
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
