package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.result;

import lombok.Data;

import java.util.List;

/**
 * @author hzh
 * @description
 * @date 2021/3/3 10:00
 */
@Data
public class SelectTreeInfoResult {
    private String id;
    private String name;
    private List<SelectTreeInfoResult> children;
}
