package com.jinninghui.datasphere.icreditstudio.datasync.container.impl;

import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractAssociatedFormatter;
import com.jinninghui.datasphere.icreditstudio.datasync.container.utils.AssociatedUtil;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedFormatterVo;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedCondition;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedData;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author peng
 */
@Component
public class MysqlAssociatedFormatter extends AbstractAssociatedFormatter {
    @Override
    public String format(AssociatedFormatterVo associatedFormatterVo) {
        List<TableInfo> sourceTables = associatedFormatterVo.getSourceTables();
        List<AssociatedData> assoc = associatedFormatterVo.getAssoc();
        String sql = "select * from ";
        String assocStr = "";
        if (CollectionUtils.isNotEmpty(sourceTables) && sourceTables.size() == 1) {
            assocStr = sourceTables.get(0).getTableName();
        } else {
            StringJoiner add = new StringJoiner(" ");
            for (AssociatedData associatedData : assoc) {
                if (StringUtils.isBlank(assocStr)) {
                    List<AssociatedCondition> conditions = associatedData.getConditions();
                    add.add(associatedData.getLeftSource());
                    String transfer = transfer(associatedFormatterVo.getDialect(), associatedData, conditions);
                    add.add(transfer);
                    assocStr = add.toString();
                } else {
                    List<AssociatedCondition> conditions = associatedData.getConditions();
                    String transfer = transfer(associatedFormatterVo.getDialect(), associatedData, conditions);
                    add.add(transfer);
                    assocStr = add.toString();
                }
            }
        }
        return new StringJoiner("").add(sql).add(assocStr).toString();
    }

    @Override
    public String getDialect() {
        return "mysql";
    }

    private String transfer(String dialect, AssociatedData ad, List<AssociatedCondition> conditions) {
        StringJoiner sj = new StringJoiner(" ");
        sj.add(AssociatedUtil.find(dialect).keyword(ad.getAssociatedType()));
        sj.add(ad.getRightSource());
        sj.add("on");
        String con = "";
        StringJoiner c = new StringJoiner(" ");
        for (AssociatedCondition condition : conditions) {
            c.add(con);
            if (c.length() != 0) {
                c.add("and");
            }
            c.add(condition.getLeft());
            c.add(condition.getAssociate());
            c.add(condition.getRight());
        }
        return sj.merge(c).toString();
    }
}
