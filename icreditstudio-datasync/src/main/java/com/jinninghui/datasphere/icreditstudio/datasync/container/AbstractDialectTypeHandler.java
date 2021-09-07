package com.jinninghui.datasphere.icreditstudio.datasync.container;

import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.Associated;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedFormatterVo;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.TableInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedCondition;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedData;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author Peng
 */
public abstract class AbstractDialectTypeHandler implements AssociatedRegister, Formatter<AssociatedFormatterVo> {

    public AbstractDialectTypeHandler() {
        register();
    }

    /**
     * 格式化
     *
     * @param associatedFormatterVo
     * @return
     */
    @Override
    public abstract String format(AssociatedFormatterVo associatedFormatterVo);

    @Override
    public void register() {
        //注册formatter
        FormatterDialectKeyContainer FormatterInstance = FormatterDialectKeyContainer.getInstance();
        FormatterInstance.put(this.getDialect(), this);
        //注册关系
        AssociatedDialectKeyContainer AssociatedInstance = AssociatedDialectKeyContainer.getInstance();
        Associated associated = new Associated();
        associated.setAssocTypes(this.getAssocTypes());
        associated.setAssocConditions(this.getAssocConditions());
        AssociatedInstance.put(this.getDialect(), associated);
    }

    public final String completion(AssociatedFormatterVo associatedFormatterVo) {
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
        return format(associatedFormatterVo);
    }
}
