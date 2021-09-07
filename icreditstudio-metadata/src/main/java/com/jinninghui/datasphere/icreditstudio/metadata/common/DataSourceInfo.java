package com.jinninghui.datasphere.icreditstudio.metadata.common;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Peng
 */
@Data
public class DataSourceInfo {
    private String driverClass;
    private String username;
    private String password;
    private String address;
    private String url;

    public String getDriverClass() {
        if (StringUtils.isBlank(driverClass)) {
            return "org.apache.hive.jdbc.HiveDriver";
        }
        return driverClass;
    }

    public String getAddress() {
        if (StringUtils.isBlank(address) && StringUtils.isNotBlank(url)) {
            address = StrUtil.subBefore(StrUtil.subAfter(url, "//", false), "/", false);
            if (StringUtils.isBlank(address)) {
                throw new RuntimeException("url格式不正确");
            }
        }
        return address;
    }

    public String getUrl() {
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException("连接url不能为空");
        }
        return url;
    }
}
