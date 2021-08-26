package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasync.service.Parser;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.FileAssociated;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Peng
 */
@Component
public class FileAssociatedParser implements Parser<String, List<FileAssociated>> {
    @Override
    public List<FileAssociated> parse(String s) {
        if (StringUtils.isNotBlank(s)) {
            return JSONArray.parseArray(s).toJavaList(FileAssociated.class);
        }
        return Lists.newArrayList();
    }
}
