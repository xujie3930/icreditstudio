package com.jinninghui.datasphere.icreditstudio.metadata.web.request;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class WorkspaceUserAuthRequest {
    /**
     * linux系统用户
     */
    private List<String> userCode;
    /**
     * 工作空间
     */
    private String workspaceId;
}
