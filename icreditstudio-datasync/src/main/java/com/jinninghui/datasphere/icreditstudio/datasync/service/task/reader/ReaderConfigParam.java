package com.jinninghui.datasphere.icreditstudio.datasync.service.task.reader;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class ReaderConfigParam {
    /**
     * 数据库用户名
     */
    private String username;
    /**
     * 数据库密码
     */
    private String password;
    /**
     * jdbcUrl
     */
    private String jdbcUrl;
    /**
     * 查询语句
     */
    private String querySql;
}
