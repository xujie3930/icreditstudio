package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasync.service.AbstractAssociated;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Peng
 */
@Component
public class OracleAssociated extends AbstractAssociated {
    @Override
    public List<AssocType> getAssocTypes() {
        return Lists.newArrayList(new AssocType(0,"左连接"),new AssocType(1,"有链接"));
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
