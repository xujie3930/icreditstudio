package com.jinninghui.datasphere.icreditstudio.datasync.container.utils;

import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractDialectTypeHandler;
import com.jinninghui.datasphere.icreditstudio.datasync.container.AssociatedDialectKeyContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.FormatterDialectKeyContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.Associated;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedFormatterVo;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.ConnectionInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;

/**
 * @author Peng
 */
public final class AssociatedUtil {

    public static Associated find(String dialect) {
        return AssociatedDialectKeyContainer.getInstance().find(dialect);
    }

    public static String wideTableSql(AssociatedFormatterVo vo) {
        AbstractDialectTypeHandler abstractDialectTypeHandler = FormatterDialectKeyContainer.getInstance().find(vo.getDialect());
        return abstractDialectTypeHandler.completion(vo);
    }

    public static ResultSetMetaData getResultSetMetaData(ConnectionInfo info, String sql) throws Exception {
        Class.forName(info.getDriverClass());
        Connection connection = DriverManager.getConnection(info.getUrl(), info.getUsername(), info.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.getMetaData();
    }
}
