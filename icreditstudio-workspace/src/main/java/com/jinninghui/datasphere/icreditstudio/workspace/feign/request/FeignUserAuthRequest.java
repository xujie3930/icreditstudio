package com.jinninghui.datasphere.icreditstudio.workspace.feign.request;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class FeignUserAuthRequest {

    /**
     * linux系统用户
     */
    private List<String> userCode;
    /**
     * 工作空间
     */
    private String workspaceId;
}
