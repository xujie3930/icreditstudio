package com.jinninghui.datasphere.icreditstudio.metadata.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author peng
 */
@ConfigurationProperties(prefix = "hive")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HiveProperties {
    private String driverClass;
    private String username;
    private String password;
    private String address;
}
