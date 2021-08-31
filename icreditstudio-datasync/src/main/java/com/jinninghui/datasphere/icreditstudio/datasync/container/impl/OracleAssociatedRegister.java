package com.jinninghui.datasphere.icreditstudio.datasync.container.impl;

import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractAssociatedRegister;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedType;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.AssociatedEnum;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Peng
 */
@Component
public class OracleAssociatedRegister extends AbstractAssociatedRegister {
    @Override
    public List<AssociatedType> getAssocTypes() {
        return Lists.newArrayList(new AssociatedType(AssociatedEnum.LEFT_JOIN, "left join")
                , new AssociatedType(AssociatedEnum.INNER_JOIN, "inner join")
                , new AssociatedType(AssociatedEnum.FULL_JOIN, "full join"));
    }

    @Override
    public List<String> getAssocConditions() {
        return Lists.newArrayList(">", "=", "<");
    }

    @Override
    public String getDialect() {
        return "oracle";
    }
}
