package com.jinninghui.datasphere.icreditstudio.datasource.service.factory;

import com.jinninghui.datasphere.icreditstudio.datasource.common.enums.DatasourceTypeEnum;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.utils.sm4.SM4Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

public interface DatasourceSync {

    Logger logger = LoggerFactory.getLogger(DatasourceSync.class);
    static final String SEPARATOR = "|";

    /**
     * 根据uri获取jdbc连接
     *
     * @param uri
     * @return
     */
    static String geturi(String uri) {
        //根据uri获取jdbc连接
        return uri.substring(0, uri.indexOf(SEPARATOR));
    }

    /**
     * 获取用户名
     *
     * @param uri
     * @return
     */
    static String getUsername(String uri) {
        //根据uri获取username
        String temp = uri.substring(uri.indexOf("username=") + "username=".length());
        String username = temp.substring(0, temp.indexOf(SEPARATOR));
        return username;
    }

    /**
     * 获取密码
     *
     * @param uri
     * @return
     */
    static String getPassword(String uri) {
        //根据uri获取password
        String temp = uri.substring(uri.indexOf("password=") + "password=".length());
        String password;
        if (!temp.endsWith(SEPARATOR)) {
            password = temp;
        } else {
            password = temp.substring(0, temp.indexOf(SEPARATOR));
        }
        SM4Utils sm4 = new SM4Utils();
        return sm4.decryptData_ECB(password);
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

    String getDatabaseName(String uri);

    default String testConn(Integer type, String uri) {
        Connection conn = null;
        String driver = DatasourceTypeEnum.findDatasourceTypeByType(type).getDriver();
        String username = getUsername(uri);
        String password = getPassword(uri);
        String jdbcUri = geturi(uri);
        try {
            Class.forName(driver);
            //超时时间由默认30s改为5秒
            DriverManager.setLoginTimeout(5);
            conn = DriverManager.getConnection(jdbcUri, username, password);
            return "测试连接成功";
        } catch (Exception e) {
            logger.error("异常信息:{},异常打印:{}", e.getMessage(), e.toString());
            throw new AppException("70000007");
        }finally {
            try {
                if(null != conn) {
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    static Connection getConn(Integer type, String uri, String username, String password) {
        String driver = DatasourceTypeEnum.findDatasourceTypeByType(type).getDriver();
        Connection connection = null;
        try {
            Class.forName(driver);
            String jdbcUri = geturi(uri);
            connection = DriverManager.getConnection(jdbcUri, username, password);
        } catch (Exception e) {
            logger.error("获取连接失败", e);
        }
        return connection;
    }

    String getHost(String uri);

    Map<String, String> syncDDL(Integer type, String uri) throws Exception;
}
