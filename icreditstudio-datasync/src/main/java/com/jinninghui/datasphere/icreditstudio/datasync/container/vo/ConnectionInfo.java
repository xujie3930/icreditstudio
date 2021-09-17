package com.jinninghui.datasphere.icreditstudio.datasync.container.vo;

import com.jinninghui.datasphere.icreditstudio.datasync.container.ConnectionSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Peng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionInfo implements ConnectionSource {
    /**
     * 连接驱动
     */
    private String driverClass;
    /**
     * 连接地址
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
