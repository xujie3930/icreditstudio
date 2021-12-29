package com.jinninghui.datasphere.icreditstudio.metadata.feign;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.metadata.feign.result.IcreditWorkspaceUserResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Peng
 */
@FeignClient(value = "workspace")
public interface WorkspaceFeign {

    /**
     * 获取工作空间下的用户
     *
     * @param workspaceId
     * @return
     */
    @GetMapping("/workspace/user/getWorkspaceUserByWorkspaceId")
    BusinessResult<List<IcreditWorkspaceUserResult>> getWorkspaceUserByWorkspaceId(@RequestParam("workspaceId") String workspaceId);
}
