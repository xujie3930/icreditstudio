package com.jinninghui.datasphere.icreditstudio.datasource.service.result;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class DatasourceResult {
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
}
