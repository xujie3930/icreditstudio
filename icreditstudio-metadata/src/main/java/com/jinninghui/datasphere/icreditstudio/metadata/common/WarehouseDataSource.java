package com.jinninghui.datasphere.icreditstudio.metadata.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Peng
 */
@Data
@Slf4j
public class WarehouseDataSource {
    private DataSourceInfo dataSourceInfo;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    public WarehouseDataSource() {
        init();
    }

    public void init() {
        try {
            Class.forName(dataSourceInfo.getDriverClass());
            conn = DriverManager.getConnection(dataSourceInfo.getUrl(), dataSourceInfo.getUsername(), dataSourceInfo.getPassword());
            stmt = conn.createStatement();
        } catch (Exception e) {
            log.error("目标源初始化连接失败", e);
        }
    }
}
