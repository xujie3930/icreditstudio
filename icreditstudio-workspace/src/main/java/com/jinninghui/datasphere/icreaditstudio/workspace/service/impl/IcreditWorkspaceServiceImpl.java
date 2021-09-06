package com.jinninghui.datasphere.icreaditstudio.workspace.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreaditstudio.workspace.entity.IcreditWorkspaceEntity;
import com.jinninghui.datasphere.icreaditstudio.workspace.entity.IcreditWorkspaceUserEntity;
import com.jinninghui.datasphere.icreaditstudio.workspace.feign.SystemFeignClient;
import com.jinninghui.datasphere.icreaditstudio.workspace.mapper.IcreditWorkspaceMapper;
import com.jinninghui.datasphere.icreaditstudio.workspace.service.IcreditWorkspaceService;
import com.jinninghui.datasphere.icreaditstudio.workspace.service.param.IcreditWorkspaceDelParam;
import com.jinninghui.datasphere.icreaditstudio.workspace.service.param.IcreditWorkspaceSaveParam;
import com.jinninghui.datasphere.icreaditstudio.workspace.web.request.IcreditWorkspaceEntityPageRequest;
import com.jinninghui.datasphere.icreaditstudio.workspace.web.request.WorkspaceHasExistRequest;
import com.jinninghui.datasphere.icreaditstudio.workspace.web.request.WorkspaceMember;
import com.jinninghui.datasphere.icreaditstudio.workspace.web.result.WorkspaceDetailResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.sequence.api.SequenceService;
import com.jinninghui.datasphere.icreditstudio.framework.utils.CollectionUtils;
import com.jinninghui.datasphere.icreditstudio.framework.validate.BusinessParamsValidate;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2021-08-20
 */
@Service
public class IcreditWorkspaceServiceImpl extends ServiceImpl<IcreditWorkspaceMapper, IcreditWorkspaceEntity> implements IcreditWorkspaceService {

    @Autowired
    private IcreditWorkspaceMapper workspaceMapper;
    @Autowired
    private IcreditWorkspaceUserServiceImpl workspaceUserService;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private SystemFeignClient systemFeignClient;

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> saveDef(IcreditWorkspaceSaveParam param) {
        Date date = new Date();
        String createUserName = param.getCreateUser().getUsername();
        IcreditWorkspaceEntity defEntity = new IcreditWorkspaceEntity();
        BeanCopyUtils.copyProperties(param, defEntity);
        defEntity.setId(sequenceService.nextValueString());
        defEntity.setCreateUser(createUserName);
        defEntity.setCreateTime(date);
        defEntity.setUpdateTime(date);
        defEntity.setUpdateUser(createUserName);
        //保存工作空间信息
        save(defEntity);
        //保存用户列表信息
        if (!CollectionUtils.isEmpty(param.getMemberList())) {
            for (WorkspaceMember member : param.getMemberList()) {
                IcreditWorkspaceUserEntity entity = new IcreditWorkspaceUserEntity();
                BeanCopyUtils.copyProperties(member, entity);
                entity.setId(sequenceService.nextValueString());
                entity.setSpaceId(defEntity.getId());
                entity.setCreateUser(createUserName);
                entity.setCreateTime(date);
                workspaceUserService.save(entity);
            }
        }
        return BusinessResult.success(true);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BusinessResult<Boolean> deleteById(IcreditWorkspaceDelParam param) {
        workspaceMapper.updateStatusById(param.getId());
        return BusinessResult.success(true);
    }

    @Override
    public BusinessPageResult queryPage(IcreditWorkspaceEntityPageRequest pageRequest) {
        Page<IcreditWorkspaceEntity> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        BusinessResult<Boolean> result = systemFeignClient.isAdmin();
        //管理员，可以查询所有数据
        if (result.isSuccess() && result.getData()){
            pageRequest.setUserId("");
        }
        return BusinessPageResult.build(page.setRecords(workspaceMapper.queryPage(page, pageRequest)), pageRequest);
    }

    @Override
    public BusinessResult<Boolean> hasExit(WorkspaceHasExistRequest request) {
        boolean hasExit = BooleanUtils.isTrue(workspaceMapper.hasExit(request));
        return BusinessResult.success(hasExit);
    }

    @Override
    public WorkspaceDetailResult getDetailById(String id) {
        WorkspaceDetailResult result = new WorkspaceDetailResult();
        IcreditWorkspaceEntity entity = getById(id);
        BeanCopyUtils.copyProperties(entity, result);
        result.setCreateTime(entity.getCreateTime().getTime());
        result.setUpdateTime(entity.getUpdateTime().getTime());
        List<IcreditWorkspaceUserEntity> memberList = workspaceUserService.queryMemberListByWorkspaceId(id);
        List<WorkspaceMember> collect = memberList.stream().map(user -> {
            WorkspaceMember member = BeanCopyUtils.copyProperties(user, new WorkspaceMember());
            member.setCreateTime(user.getCreateTime().getTime());
            return member;
        }).collect(Collectors.toList());
        result.setMemberList(collect);
        return result;
    }
}