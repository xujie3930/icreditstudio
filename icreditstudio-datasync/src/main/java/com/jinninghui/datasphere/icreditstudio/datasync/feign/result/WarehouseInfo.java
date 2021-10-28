package com.jinninghui.datasphere.icreditstudio.datasync.feign.result;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class WarehouseInfo {
    /**
     * hive连接用户名
     */
    private String user;
    /**
     * hive连接密码
     */
    private String passWord;
    /**
     * hive连接url
     */
    private String thriftUrl;
    /**
     * hdfs服务地址
     */
    private String defaultFS;
    /**
     * 数据存放路径
     */
    private String warehouse;
}
