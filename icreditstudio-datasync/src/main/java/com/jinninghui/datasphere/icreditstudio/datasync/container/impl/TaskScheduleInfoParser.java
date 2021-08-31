package com.jinninghui.datasphere.icreditstudio.datasync.container.impl;

import com.alibaba.fastjson.JSONObject;
import com.jinninghui.datasphere.icreditstudio.datasync.container.Parser;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.TaskScheduleInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author Peng
 */
@Component
public class TaskScheduleInfoParser implements Parser<String, TaskScheduleInfo> {
    @Override
    public TaskScheduleInfo parse(String s) {
        if (StringUtils.isNotBlank(s)) {
            return JSONObject.parseObject(s).toJavaObject(TaskScheduleInfo.class);
        }
        return null;
    }
}
