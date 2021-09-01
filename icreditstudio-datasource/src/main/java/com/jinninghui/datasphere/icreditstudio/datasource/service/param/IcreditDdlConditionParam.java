package com.jinninghui.datasphere.icreditstudio.datasource.service.param;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

/**
 * @author Peng
 */
@Data
@Builder
public class IcreditDdlConditionParam {
    private Collection<String> datasourceIds;
}
