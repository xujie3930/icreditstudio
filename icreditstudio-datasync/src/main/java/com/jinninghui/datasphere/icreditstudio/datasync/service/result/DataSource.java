package com.jinninghui.datasphere.icreditstudio.datasync.service.result;

import lombok.Data;

/**
 * @author Peng
 */
@Data
public class DataSource {
    /**
     * 名称
     */
    private String name;
    /**
     * 表信息主键
     */
    private String id;
    /**
     * 链接地址
     */
    private String url;
}
