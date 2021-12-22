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
import com.jinninghui.datasphere.icreditstudio.framework.common.enums.DialectEnum;
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
public class OracleTypeHandler extends AbstractDialectTypeHandler {
    Formatter<AssociatedFormatterVo> typeHandler = new OracleQueryStatementFormatter();

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
        return DialectEnum.ORACLE.getDialect();
    }

    @Override
    public String format(AssociatedFormatterVo vo) {
        Objects.requireNonNull(vo);

        List<TableInfo> sourceTables = vo.getSourceTables();
        List<TableInfo> collect = Optional.ofNullable(sourceTables).orElse(Lists.newArrayList())
                .stream()
                .map(s -> {
                    String database = s.getDatabase();
                    s.setTableName(new StringJoiner(".").add(database).add("\"").add(s.getTableName()).add("\"").toString());
                    return s;
                }).collect(Collectors.toList());
        vo.setSourceTables(collect);

        List<AssociatedData> assoc = vo.getAssoc();
        Optional.ofNullable(assoc).orElse(Lists.newArrayList())
                .stream()
                .forEach(associatedData -> {
                    String leftSource = associatedData.getLeftSource();
                    String leftSourceDatabase = associatedData.getLeftSourceDatabase();
                    StringJoiner leftSourceJoiner = new StringJoiner(".").add(leftSourceDatabase).add("\"").add(leftSource).add("\"");
                    associatedData.setLeftSource(leftSourceJoiner.toString());

                    String rightSource = associatedData.getRightSource();
                    String rightSourceDatabase = associatedData.getRightSourceDatabase();
                    StringJoiner rightSourceJoiner = new StringJoiner(".").add(rightSourceDatabase).add("\"").add(rightSource).add("\"");
                    associatedData.setRightSource(rightSourceJoiner.toString());

                    List<AssociatedCondition> conditions = associatedData.getConditions();
                    Optional.ofNullable(conditions).orElse(Lists.newArrayList())
                            .stream().forEach(associatedCondition -> {
                        String left = associatedCondition.getLeft();
                        associatedCondition.setLeft(new StringJoiner(".").merge(leftSourceJoiner).add("\"").add(left).add("\"").toString());

                        String right = associatedCondition.getRight();
                        associatedCondition.setRight(new StringJoiner(".").merge(rightSourceJoiner).add("\"").add(right).add("\"").toString());
                    });
                });
        return typeHandler.format(vo);
    }
}
