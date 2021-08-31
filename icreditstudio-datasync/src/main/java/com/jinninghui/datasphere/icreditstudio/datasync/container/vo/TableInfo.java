package com.jinninghui.datasphere.icreditstudio.datasync.container.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Peng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableInfo {
    private String database;
    private String tableName;
}
