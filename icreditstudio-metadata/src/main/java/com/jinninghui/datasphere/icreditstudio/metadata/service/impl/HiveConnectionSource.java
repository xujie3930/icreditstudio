package com.jinninghui.datasphere.icreditstudio.metadata.service.impl;

import com.google.common.collect.Sets;
import com.jinninghui.datasphere.icreditstudio.metadata.common.HiveProperties;
import com.jinninghui.datasphere.icreditstudio.metadata.service.AbstractClusterHiveConnectionSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hive.jdbc.HiveDriver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author peng
 */
@Slf4j
@Component
@ConditionalOnClass({HiveDriver.class})
@EnableConfigurationProperties(HiveProperties.class)
public class HiveConnectionSource extends AbstractClusterHiveConnectionSource {

    public static final String DEFAULT_HIVE_DRIVER_CLASS = "org.apache.hive.jdbc.HiveDriver";
    public static final String DEFAULT_PORT = "10000";
    @Resource
    private HiveProperties hiveProperties;

    @PostConstruct
    public void init() {
        if (Objects.isNull(hiveProperties)) {
            try {
                throw new RuntimeException("未配置hive连接信息");
            } finally {
                Runtime.getRuntime().exit(0);
            }
        }
        try {
            Class.forName(getDriverClass());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public String getDriverClass() {
        String driverClass = hiveProperties.getDriverClass();
        if (StringUtils.isBlank(driverClass)) {
            driverClass = DEFAULT_HIVE_DRIVER_CLASS;
        }
        return driverClass;
    }

    @Override
    public String getUsername() {
        String username = hiveProperties.getUsername();
        if (StringUtils.isBlank(username)) {
            try {
                throw new RuntimeException("未配置hive访问用户名");
            } finally {
                Runtime.getRuntime().exit(0);
            }
        }
        return username;
    }

    @Override
    public String getPassword() {
        String password = hiveProperties.getPassword();
        if (StringUtils.isBlank(password)) {
            try {
                throw new RuntimeException("未配置hive访问密码");
            } finally {
                Runtime.getRuntime().exit(0);
            }
        }
        return password;
    }

    @Override
    public Set<String> getIpPorts() {
        String nodes = hiveProperties.getNodes();
        if (StringUtils.isBlank(nodes)) {
            try {
                throw new RuntimeException("未配置hive链接地址");
            } finally {
                Runtime.getRuntime().exit(0);
            }
        }
        String[] addrs = StringUtils.split(nodes, ",");
        if (Objects.isNull(addrs) || addrs.length == 0) {
            try {
                throw new RuntimeException("未配置正确的hive链接地址");
            } finally {
                Runtime.getRuntime().exit(0);
            }
        }
        Set<String> notBlankAddrs = Arrays.stream(addrs)
                .filter(StringUtils::isNoneBlank)
                .collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(notBlankAddrs)) {
            try {
                throw new RuntimeException("未配置正确的hive链接地址");
            } finally {
                Runtime.getRuntime().exit(0);
            }
        }
        Set<String> ipPorts = Sets.newHashSet();
        notBlankAddrs.stream()
                .forEach(addr -> {
                    if (addr.contains(":")) {
                        ipPorts.add(addr);
                    } else {
                        ipPorts.add(new StringJoiner(":").add(addr).add(DEFAULT_PORT).toString());
                    }
                });
        return ipPorts;
    }
}
