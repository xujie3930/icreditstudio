package com.jinninghui.datasphere.icreditstudio.datasync.container.utils;

import com.jinninghui.datasphere.icreditstudio.datasync.container.AbstractDialectTypeHandler;
import com.jinninghui.datasphere.icreditstudio.datasync.container.impl.AssociatedDialectKeyContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.impl.FormatterDialectKeyContainer;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.Associated;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.AssociatedFormatterVo;
import com.jinninghui.datasphere.icreditstudio.datasync.container.vo.ConnectionInfo;

import java.sql.*;

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

    public static ResultSetMetaData getResultSetMetaData(Connection connection, String sql) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.getMetaData();
    }

    public static DatabaseMetaData getDatabaseMetaData(Connection connection) throws Exception {
        return connection.getMetaData();
    }

    public static Connection getConnection(ConnectionInfo info) throws Exception {
        Class.forName(info.getDriverClass());
        return DriverManager.getConnection(info.getUrl(), info.getUsername(), info.getPassword());
    }
}
