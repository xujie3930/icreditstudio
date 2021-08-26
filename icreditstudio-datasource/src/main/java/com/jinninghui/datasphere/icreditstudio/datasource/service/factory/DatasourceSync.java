package com.jinninghui.datasphere.icreditstudio.datasource.service.factory;

import com.jinninghui.datasphere.icreditstudio.datasource.common.enums.DatasourceTypeEnum;

import java.sql.Connection;
import java.sql.DriverManager;

public interface DatasourceSync {
    //获取username
    default String getUsername(String uri){
        //根据uri获取username
        String temp = uri.substring(uri.indexOf("username=") + "username=".length());
        String username = temp.substring(0, temp.indexOf("&"));
        return username;
    }
    //获取password
    default String getpassword(String uri){
        //根据uri获取password
        String temp = uri.substring(uri.indexOf("password=") + "password=".length());
        String password;
        if (!temp.endsWith("&")){
            password = temp;
        }else {
            password = temp.substring(0, temp.indexOf("&"));
        }
        return password;
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

    String syncDDL(Integer type, String uri) throws Exception;
}
