package com.jinninghui.datasphere.icreditstudio.metadata.service.param;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class HiveAuthInfo {
    /**
     * 用户名称
     */
    private String userName;
    /**
     * hive库名
     */
    private String hiveBaseName;
    /**
     * hive表名称
     */
    private String hiveTable;
    /**
     * 权限
     */
    private List<String> perms;
}
