package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Peng
 */
@Data
public class CronParam {

    private String type;

    private List<Map<String, Integer>> moment;
}
