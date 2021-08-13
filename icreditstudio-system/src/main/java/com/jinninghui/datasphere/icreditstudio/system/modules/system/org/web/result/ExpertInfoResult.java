package com.jinninghui.datasphere.icreditstudio.system.modules.system.org.web.result;

import lombok.Data;

import java.util.List;

/**
 * @author hzh
 * @description
 * @date 2021/3/5 16:33
 */
@Data
public class ExpertInfoResult {

    private Integer successCount;

    private Integer errorCount;

    List<Object> noPassList;


}
