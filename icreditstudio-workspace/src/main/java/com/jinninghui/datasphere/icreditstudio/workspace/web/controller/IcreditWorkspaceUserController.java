package com.jinninghui.datasphere.icreditstudio.workspace.web.controller;


import com.jinninghui.datasphere.icreditstudio.workspace.service.IcreditWorkspaceUserService;
import com.jinninghui.datasphere.icreditstudio.workspace.web.request.IcreditWorkspaceUserEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.framework.log.Logable;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
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


    @GetMapping("/getWorkspaceByUserId/{id}")
    @Logable
    public BusinessResult<List<Map<String, String>>> getWorkspaceListByUserId(@PathVariable("id") String id) {
        return BusinessResult.success(workspaceUserService.getWorkspaceByUserId(id));
    }

}

