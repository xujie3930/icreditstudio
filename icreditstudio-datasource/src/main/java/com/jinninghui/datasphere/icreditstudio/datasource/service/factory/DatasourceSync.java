package com.jinninghui.datasphere.icreditstudio.datasource.service.factory;

import com.jinninghui.datasphere.icreditstudio.datasource.common.enums.DatasourceTypeEnum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import java.util.Objects;

public interface DatasourceSync {
    /**
     * 获取用户名
     *
     * @param uri
     * @return
     */
    static String getUsername(String uri) {
        //根据uri获取username
        String temp = uri.substring(uri.indexOf("username=") + "username=".length());
        String username = temp.substring(0, temp.indexOf("|"));
        return username;
    }

    /**
     * 获取密码
     *
     * @param uri
     * @return
     */
    static String getpassword(String uri) {
        //根据uri获取password
        String temp = uri.substring(uri.indexOf("password=") + "password=".length());
        String password;
        if (!temp.endsWith("|")) {
            password = temp;
        } else {
            password = temp.substring(0, temp.indexOf("|"));
        }
        return password;
    }

    /**
     * 获取连接url
     *
     * @param uri
     * @return
     */
    static String getConnUrl(String uri) {
        String[] split = uri.split("\\|");
        if (Objects.nonNull(split)) {
            return split[0];
        }
        return null;
    }

    default String testConn(Integer type, String uri) {
        String driver = DatasourceTypeEnum.findDatasourceTypeByType(type).getDriver();
        String username = getUsername(uri);
        String password = getpassword(uri);
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(uri, username, password);
            conn.close();
            return "测试连接成功";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    default Connection getConn(Integer type, String uri, String username, String password) {
        String driver = DatasourceTypeEnum.findDatasourceTypeByType(type).getDriver();
        Connection connection;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(uri, username, password);
        } catch (Exception e) {
            return null;
        }
        return connection;
    }

    Map<String, String> syncDDL(Integer type, String uri) throws Exception;
}
