package com.jinninghui.datasphere.icreditstudio.datasource.service.factory;

import com.jinninghui.datasphere.icreditstudio.datasource.common.enums.DatasourceTypeEnum;

import java.sql.Connection;
import java.sql.DriverManager;

public interface DatasourceSync {
    //获取username
    default String getUsername(String uri){
        //根据uri获取username和password
        String temp = uri.substring(uri.indexOf("username=") + "username=".length());
        String username = temp.substring(0, temp.indexOf("&"));
        return username;
    }
    //获取password
    default String getpassword(String uri){
        //根据uri获取username和password
        String temp = uri.substring(uri.indexOf("password=") + "password=".length());
        String username = temp.substring(0, temp.indexOf("&"));
        return username;
    }
    default String testConn(Integer type, String uri, String username, String password) {
        String driver = DatasourceTypeEnum.findDriverByType(type).getDriver();
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(uri, username, password);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "测试连接成功";
    }

    default Connection getConn(Integer type, String uri, String username, String password) {
        String driver = DatasourceTypeEnum.findDriverByType(type).getDriver();
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(uri, username, password);
        } catch (Exception e) {
            return connection;
        }
        return connection;
    }

    String syncDDL(Integer type, String uri) throws Exception;
}
