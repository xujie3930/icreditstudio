package com.jinninghui.datasphere.icreditstudio.datasource.service.factory;

//import cn.hutool.core.util.StrUtil;

import cn.hutool.core.util.StrUtil;
import com.jinninghui.datasphere.icreditstudio.datasource.common.enums.DatasourceTypeEnum;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
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

    /**
     * 取得数据库名称
     *
     * @param uri
     * @return
     */
    static String getDatabaseName(String uri) {
        String s = StrUtil.subBefore(uri, "?", false);
        return StrUtil.subAfter(s, "/", true);
    }

    default String testConn(Integer type, String uri) {
        String driver = DatasourceTypeEnum.findDatasourceTypeByType(type).getDriver();
        String username = getUsername(uri);
        String password = getPassword(uri);
        String jdbcUri = geturi(uri);
        try {
            Class.forName(driver);
            //超时时间由默认30s改为5秒
            DriverManager.setLoginTimeout(5);
            Connection conn = DriverManager.getConnection(jdbcUri, username, password);
            conn.close();
            return "测试连接成功";
        } catch (Exception e) {
            logger.error("异常信息:{},异常打印:{}", e.getMessage(), e.toString());
            throw new AppException("70000007");
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

    static String getHost(String uri) {
        if (StringUtils.isNotBlank(uri)) {
            String temp = StrUtil.subAfter(uri, "//", false);
            return StrUtil.subBefore(temp, "/", false);
        }
        return null;
    }

    Map<String, String> syncDDL(Integer type, String uri) throws Exception;
}
