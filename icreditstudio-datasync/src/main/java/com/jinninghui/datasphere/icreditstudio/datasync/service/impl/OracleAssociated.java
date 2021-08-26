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
public class OracleAssociated extends AbstractAssociated {
    @Override
    public List<AssociatedType> getAssocTypes() {
        return Lists.newArrayList(new AssociatedType(0,"左连接"),new AssociatedType(1,"有链接"));
    }

    @Override
    public List<String> getAssocConditions() {
        return Lists.newArrayList("大于","等于","小于");
    }

    @Override
    public String getDialect() {
        return "oracle";
    }
}
