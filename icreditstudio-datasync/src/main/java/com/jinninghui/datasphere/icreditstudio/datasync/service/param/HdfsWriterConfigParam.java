package com.jinninghui.datasphere.icreditstudio.datasync.service.param;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Peng
 */
@Data
public class HdfsWriterConfigParam {

    private String defaultFs;
    private String fileType;
    private String path;
    private String fileName;
    private String writeMode;
    private String fieldDelimiter;
    private String partition;
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
    private String compress;

    public String getFileType() {
        if (StringUtils.isBlank(fileType)) {
            return "orc";
        }
        return fileType;
    }

    public String getFieldDelimiter() {
        if (StringUtils.isBlank(fieldDelimiter)) {
            return ",";
        }
        return fieldDelimiter;
    }

    public String getCompress() {
        if (StringUtils.isBlank(compress)) {
            return "NONE";
        }
        return compress;
    }

    public String getWriteMode() {
        if (StringUtils.isBlank(writeMode)) {
            return "append";
        }
        return writeMode;
    }

    public String getThriftUrl() {
        if (StringUtils.isNotBlank(thriftUrl) && thriftUrl.endsWith("default")) {
            return StringUtils.replace(thriftUrl, "default", "");
        }
        return thriftUrl;
    }
}
