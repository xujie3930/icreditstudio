package com.jinninghui.datasphere.icreditstudio.metadata.service.impl;

import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.metadata.service.AbstractClusterHiveConnectionSource;
import com.jinninghui.datasphere.icreditstudio.metadata.service.MetadataConnection;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Set;

/**
 * @author Peng
 */
@Slf4j
@Component
public class HiveConnection implements MetadataConnection {

    public static final String HIVE_URL_PREFIX = "jdbc:hive2://";
    public static final String DEFAULT_DATABASE = "default";
    @Autowired
    private AbstractClusterHiveConnectionSource connectionSource;

    @Override
    public Connection getConnection() {
        String username = connectionSource.getUsername();
        String password = connectionSource.getPassword();
        Set<String> ipPorts = connectionSource.getIpPorts();

        Connection connection = null;
        if (CollectionUtils.isNotEmpty(ipPorts)) {
            for (String ipPort : ipPorts) {
                String url = joiningHiveUrl(ipPort);
                try {
                    connection = DriverManager.getConnection(url, username, password);
                    if (Objects.nonNull(connection)) {
                        break;
                    }
                } catch (SQLException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        if (Objects.isNull(connection)) {
            throw new AppException("80000002");
        }
        return connection;
    }

    /**
     * 拼接hive连接信息
     *
     * @param ipPort
     * @return
     */
    private String joiningHiveUrl(String ipPort) {
        StringBuilder sb = new StringBuilder();
        sb.append(HIVE_URL_PREFIX);
        sb.append(ipPort);
        sb.append(DEFAULT_DATABASE);
        return sb.toString();
    }
}
