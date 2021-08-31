package com.jinninghui.datasphere.icreditstudio.datasync.container;

import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedFormatterVo;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedType;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedCondition;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedData;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author peng
 */
public abstract class AbstractAssociatedFormatter implements AssociatedFormatter<AssociatedFormatterVo>, AssociatedRegister {

    public AbstractAssociatedFormatter() {
        register();
    }

    public abstract String format(AssociatedFormatterVo associatedFormatterVo);

    @Override
    public String template() {
        return null;
    }

    @Override
    public List<AssociatedType> getAssocTypes() {
        return null;
    }

    @Override
    public List<String> getAssocConditions() {
        return null;
    }

    @Override
    public void register() {
        FormatterDialectKeyContainer instance = FormatterDialectKeyContainer.getInstance();
        instance.put(this.getDialect(), this);
    }

    public final String completion(AssociatedFormatterVo associatedFormatterVo) {
        Objects.requireNonNull(associatedFormatterVo);
        String database = associatedFormatterVo.getDatabase();

        List<String> sourceTables = associatedFormatterVo.getSourceTables();
        List<String> collect = Optional.ofNullable(sourceTables).orElse(Lists.newArrayList())
                .stream()
                .map(s -> new StringJoiner(".").add(database).add(s).toString())
                .collect(Collectors.toList());
        associatedFormatterVo.setSourceTables(collect);

        List<AssociatedData> assoc = associatedFormatterVo.getAssoc();
        Optional.ofNullable(assoc).orElse(Lists.newArrayList())
                .stream().forEach(associatedData -> {
            String leftSource = associatedData.getLeftSource();
            StringJoiner leftSourceJoiner = new StringJoiner(".").add(database).add(leftSource);
            associatedData.setLeftSource(leftSourceJoiner.toString());

            String rightSource = associatedData.getRightSource();
            StringJoiner rightSourceJoiner = new StringJoiner(".").add(database).add(rightSource);
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
