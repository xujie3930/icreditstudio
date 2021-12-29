package com.jinninghui.datasphere.icreditstudio.workspace.web.controller;


import com.jinninghui.datasphere.icreditstudio.framework.log.Logable;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.workspace.entity.IcreditWorkspaceUserEntity;
import com.jinninghui.datasphere.icreditstudio.workspace.service.IcreditWorkspaceUserService;
import com.jinninghui.datasphere.icreditstudio.workspace.web.request.IcreditWorkspaceUserEntityPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xujie
 * @since 2021-08-23
 */
@RestController
@RequestMapping("/workspace/user")
public class IcreditWorkspaceUserController {

    @Autowired
    private IcreditWorkspaceUserService workspaceUserService;

    /**
     * 分页查询列表
     */
    @PostMapping("/pageList")
    @Logable
    public BusinessResult<BusinessPageResult> userPageList(@RequestBody IcreditWorkspaceUserEntityPageRequest pageRequest) {
        BusinessPageResult page = workspaceUserService.queryPage(pageRequest);
        return BusinessResult.success(page);
    }


    @GetMapping(value = {"/getWorkspaceByUserId/{id}", "/getWorkspaceByUserId"})
    @Logable
    public BusinessResult<List<Map<String, String>>> getWorkspaceListByUserId(
            @PathVariable(value = "id",
                    required = false) String id) {
        return BusinessResult.success(workspaceUserService.getWorkspaceByUserId(id));
    }

    @GetMapping("/getWorkSpaceIdsByUserId")
    @Logable
    public List<String> getWorkSpaceIdsByUserId(@RequestParam("userId") String userId) {
        return workspaceUserService.getWorkSpaceIdsByUserId(userId);
    }

    /**
     * 根据工作空间查询空间下的用户
     *
     * @param workspaceId
     * @return
     */
    @GetMapping("/getWorkspaceUserByWorkspaceId")
    public BusinessResult<List<IcreditWorkspaceUserEntity>> getWorkspaceUserByWorkspaceId(@RequestParam("workspaceId") String workspaceId) {
        return workspaceUserService.getWorkspaceUserByWorkspaceId(workspaceId);
    }
}

