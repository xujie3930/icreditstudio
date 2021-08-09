package com.jinninghui.datasphere.icreditstudio.modules.uaa.service.result;

import lombok.Data;

/**
 * @author hzh
 * @description
 * @date 2021/1/21 14:51
 */
@Data
public class InterfaceUserAuthResult {

    /**
     * 数据库主键
     */
    private String interfaceId;
    /**
     * 接口URI
     */
    private String uri;

}
