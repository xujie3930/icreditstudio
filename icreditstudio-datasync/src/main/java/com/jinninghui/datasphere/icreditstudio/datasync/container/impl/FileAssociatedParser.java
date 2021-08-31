package com.jinninghui.datasphere.icreditstudio.datasync.container.impl;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasync.container.Parser;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Peng
 */
@Component
public class FileAssociatedParser implements Parser<String, List<AssociatedData>> {
    @Override
    public List<AssociatedData> parse(String s) {
        if (StringUtils.isNotBlank(s)) {
            return JSONArray.parseArray(s).toJavaList(AssociatedData.class);
        }
        return Lists.newArrayList();
    }
}
