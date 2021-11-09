package com.jinninghui.datasphere.icreditstudio.datasync.container.impl;

import cn.hutool.core.util.StrUtil;
import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractDataSyncQuery;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.QueryField;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author Peng
 */
@Component
public class JdbcDataSyncQuery extends AbstractDataSyncQuery {
    @Override
    public String querySql(List<QueryField> queryFields, String srcSql) {

        String result = null;
        if (CollectionUtils.isNotEmpty(queryFields) && StringUtils.isNotBlank(srcSql)) {
            List<String> collect = queryFields.stream()
                    .map(field -> new StringJoiner(".").add(field.getDatabaseName()).add(field.getSourceTable()).add(field.getFieldName()).toString())
                    .collect(Collectors.toList());
            StringJoiner sj = new StringJoiner(",");
            for (String s : collect) {
                sj.add(s);
            }

            String from = StrUtil.subAfter(srcSql, "from", true);
            StringJoiner sql = new StringJoiner(" ");
            sql.add("select");
            sql.add(sj.toString());
            sql.add("from");
            sql.add(from);
            result = sql.toString();
        } else {
            result = srcSql;
        }
        return result;
    }

    @Override
    public String getDialect() {
        return "mysql";
    }
}
