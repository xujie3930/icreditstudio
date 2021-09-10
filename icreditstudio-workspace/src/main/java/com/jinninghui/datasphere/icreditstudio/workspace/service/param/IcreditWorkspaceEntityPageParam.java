package com.jinninghui.datasphere.icreditstudio.workspace.service.param;

import com.jinninghui.datasphere.icreditstudio.framework.result.base.BusinessBasePageForm;
import lombok.Data;

import java.util.Date;

/**
 * @author 1
 */
@Data
public class IcreditWorkspaceEntityPageParam extends BusinessBasePageForm {
    private String spaceId;//工作空间id
    private String name;//工作空间名称
    private String updateUser;//更新人
    private Date updateStartTime;//初始时间
    private Date updateEndTime;//结束时间
    private String userId;//用户id
}
