package com.jinninghui.datasphere.icreaditstudio.workspace.web.controller;


import com.jinninghui.datasphere.icreaditstudio.workspace.entity.IcreditWorkspaceEntity;
import com.jinninghui.datasphere.icreaditstudio.workspace.service.IcreditWorkspaceService;
import com.jinninghui.datasphere.icreaditstudio.workspace.service.param.IcreditWorkspaceDelParam;
import com.jinninghui.datasphere.icreaditstudio.workspace.service.param.IcreditWorkspaceSaveParam;
import com.jinninghui.datasphere.icreaditstudio.workspace.web.request.*;
import com.jinninghui.datasphere.icreaditstudio.workspace.web.result.WorkspaceDetailResult;
import com.jinninghui.datasphere.icreditstudio.framework.log.Logable;
import com.jinninghui.datasphere.icreditstudio.framework.result.BaseController;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xujie
 * @since 2021-08-20
 */
@RestController
@RequestMapping("/workspace")
public class IcreditWorkspaceController extends BaseController<IcreditWorkspaceEntity, IcreditWorkspaceService> {

    @Autowired
    private IcreditWorkspaceService workspaceService;

    /**
     * 判断命名空间是否重复存在
     */
    @PostMapping("/hasExist")
    @Logable
    public BusinessResult<Boolean> info(@RequestBody WorkspaceHasExistRequest request) {
        return workspaceService.hasExit(request);
    }

    /**
     * 新增工作空间
     */
    @PostMapping("/save")
    @Logable
    public BusinessResult<Boolean> publish(@RequestHeader("x-userid") String userId, @RequestBody IcreditWorkspaceSaveRequest request) {

        IcreditWorkspaceSaveParam param = new IcreditWorkspaceSaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return workspaceService.saveDef(param);
    }

    /**
     * 更新工作空间
     */
    @PostMapping("/update")
    @Logable
    public BusinessResult<Boolean> update(@RequestHeader("x-userid") String userId, @RequestBody IcreditWorkspaceUpdateRequest request) {

        IcreditWorkspaceEntity entity = new IcreditWorkspaceEntity();
        BeanCopyUtils.copyProperties(request, entity);
        entity.setUpdateTime(new Date());
        return BusinessResult.success(workspaceService.updateById(entity));
    }

    /**
     * 更新工作空间
     */
    @PostMapping("/delete")
    @Logable
    public BusinessResult<Boolean> deleteById(@RequestBody IcreditWorkspaceDelRequest request) {
        IcreditWorkspaceDelParam param = new IcreditWorkspaceDelParam();
        BeanCopyUtils.copyProperties(request, param);
        return workspaceService.deleteById(param);
    }

    /**
     * 根据主键id查询信息
     */
    @GetMapping("/info/{id}")
    @Logable
    public BusinessResult<WorkspaceDetailResult> info(@PathVariable("id") String id) {
        WorkspaceDetailResult result = workspaceService.getDetailById(id);
        return BusinessResult.success(result);
    }

    /**
     * 分页查询列表
     */
    @PostMapping("/pageList")
    @Logable
    public BusinessResult<BusinessPageResult> pageList(@RequestHeader("x-userid") String userId, @RequestBody IcreditWorkspaceEntityPageRequest pageRequest) {
        if (StringUtils.isBlank(pageRequest.getUserId())){
            pageRequest.setUserId(userId);
        }
        BusinessPageResult page = workspaceService.queryPage(pageRequest);
        return BusinessResult.success(page);
    }
}

