package com.jinninghui.datasphere.icreditstudio.datasync.container.impl;

import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractDialectTypeHandler;
import com.jinninghui.datasphere.icreditstudio.datasync.container.Formatter;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedFormatterVo;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedType;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.AssociatedEnum;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedCondition;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author Peng
 */
@Component
public class MysqlTypeHandler extends AbstractDialectTypeHandler {
    Formatter<AssociatedFormatterVo> typeHandler = new MysqlQueryStatementFormatter();

    @Override
    public String format(AssociatedFormatterVo associatedFormatterVo) {
        Objects.requireNonNull(associatedFormatterVo);

        List<TableInfo> sourceTables = associatedFormatterVo.getSourceTables();
        List<TableInfo> collect = Optional.ofNullable(sourceTables).orElse(Lists.newArrayList())
                .stream()
                .map(s -> {
                    String database = s.getDatabase();
                    s.setTableName(new StringJoiner(".").add(database).add(s.getTableName()).toString());
                    return s;
                }).collect(Collectors.toList());
        associatedFormatterVo.setSourceTables(collect);

        List<AssociatedData> assoc = associatedFormatterVo.getAssoc();
        Optional.ofNullable(assoc).orElse(Lists.newArrayList())
                .stream()
                .forEach(associatedData -> {
                    String leftSource = associatedData.getLeftSource();
                    String leftSourceDatabase = associatedData.getLeftSourceDatabase();
                    StringJoiner leftSourceJoiner = new StringJoiner(".").add(leftSourceDatabase).add(leftSource);
                    associatedData.setLeftSource(leftSourceJoiner.toString());

                    String rightSource = associatedData.getRightSource();
                    String rightSourceDatabase = associatedData.getRightSourceDatabase();
                    StringJoiner rightSourceJoiner = new StringJoiner(".").add(rightSourceDatabase).add(rightSource);
                    associatedData.setRightSource(rightSourceJoiner.toString());

                    List<AssociatedCondition> conditions = associatedData.getConditions();
                    Optional.ofNullable(conditions).orElse(Lists.newArrayList())
                            .stream().forEach(associatedCondition -> {
                        String left = associatedCondition.getLeft();
                        associatedCondition.setLeft(new StringJoiner(".").merge(leftSourceJoiner).add(left).toString());

                        String right = associatedCondition.getRight();
                        associatedCondition.setRight(new StringJoiner(".").merge(rightSourceJoiner).add(right).toString());
                    });
                });
        return typeHandler.format(associatedFormatterVo);
    }

    @Override
    public List<AssociatedType> getAssocTypes() {
        return Lists.newArrayList(new AssociatedType(AssociatedEnum.LEFT_JOIN, "left join"), new AssociatedType(AssociatedEnum.INNER_JOIN, "inner join"));
    }

    @Override
    public List<String> getAssocConditions() {
        return Lists.newArrayList("=");
    }

    @Override
    public String getDialect() {
        return "mysql";
    }
}
