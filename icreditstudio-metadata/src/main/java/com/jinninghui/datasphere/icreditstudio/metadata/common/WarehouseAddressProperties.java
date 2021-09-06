package com.jinninghui.datasphere.icreditstudio.metadata.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * @author Peng
 */
@Data
@ConfigurationProperties(prefix = "warehouse.hive")
public class WarehouseAddressProperties {
    private List<DataSourceInfo> address = new CopyOnWriteArrayList<>();
}
