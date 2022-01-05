package com.jinninghui.datasphere.icreditstudio.workspace.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.sequence.api.SequenceService;
import com.jinninghui.datasphere.icreditstudio.framework.utils.CollectionUtils;
import com.jinninghui.datasphere.icreditstudio.framework.utils.DateUtils;
import com.jinninghui.datasphere.icreditstudio.framework.utils.StringUtils;
import com.jinninghui.datasphere.icreditstudio.framework.validate.BusinessParamsValidate;
import com.jinninghui.datasphere.icreditstudio.workspace.common.code.ResourceCodeBean;
import com.jinninghui.datasphere.icreditstudio.workspace.common.enums.WorkspaceStatusEnum;
import com.jinninghui.datasphere.icreditstudio.workspace.entity.IcreditWorkspaceEntity;
import com.jinninghui.datasphere.icreditstudio.workspace.entity.IcreditWorkspaceUserEntity;
import com.jinninghui.datasphere.icreditstudio.workspace.feign.DatasourceFeignClient;
import com.jinninghui.datasphere.icreditstudio.workspace.feign.SchedulerFeign;
import com.jinninghui.datasphere.icreditstudio.workspace.feign.SystemFeignClient;
import com.jinninghui.datasphere.icreditstudio.workspace.mapper.IcreditWorkspaceMapper;
import com.jinninghui.datasphere.icreditstudio.workspace.service.IcreditWorkspaceService;
import com.jinninghui.datasphere.icreditstudio.workspace.service.param.IcreditWorkspaceDelParam;
import com.jinninghui.datasphere.icreditstudio.workspace.service.param.IcreditWorkspaceEntityPageParam;
import com.jinninghui.datasphere.icreditstudio.workspace.service.param.IcreditWorkspaceSaveParam;
import com.jinninghui.datasphere.icreditstudio.workspace.service.param.IcreditWorkspaceUpdateParam;
import com.jinninghui.datasphere.icreditstudio.workspace.web.request.IcreditWorkspaceEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.workspace.web.request.WorkspaceHasExistRequest;
import com.jinninghui.datasphere.icreditstudio.workspace.web.request.WorkspaceMember;
import com.jinninghui.datasphere.icreditstudio.workspace.web.result.WorkBenchResult;
import com.jinninghui.datasphere.icreditstudio.workspace.web.result.WorkspaceDetailResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2021-08-20
 */
@Slf4j
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
    @Autowired
    private DatasourceFeignClient datasourceFeignClient;
    @Autowired
    private SchedulerFeign schedulerFeign;
    private static final String DEFAULT_WORKSPACEID = "0";
    private static final String SEPARATOR = ",";

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> saveDef(String userId, IcreditWorkspaceSaveParam param) {
        Date date = new Date();
        String createUserName = param.getCreateUser();
        //保存工作空间信息
        IcreditWorkspaceEntity defEntity = new IcreditWorkspaceEntity();
        BeanCopyUtils.copyProperties(param, defEntity);
        defEntity.setId(sequenceService.nextValueString());
        defEntity.setCreateUser(createUserName);
        defEntity.setCreateTime(date);
        defEntity.setUpdateTime(date);
        defEntity.setUpdateUser(createUserName);
        save(defEntity);
        //保存用户列表信息
        if (!CollectionUtils.isEmpty(param.getMemberList())) {
            for (int i = 0; i < param.getMemberList().size(); i++) {
                IcreditWorkspaceUserEntity entity = getNewMember(param.getMemberList().get(i), defEntity);
                entity.setSort(i);
                workspaceUserService.save(entity);
            }
        }
        return BusinessResult.success(true);
    }

    private IcreditWorkspaceUserEntity getNewMember(WorkspaceMember member, IcreditWorkspaceEntity defEntity) {
        IcreditWorkspaceUserEntity newMember = new IcreditWorkspaceUserEntity();
        BeanCopyUtils.copyProperties(member, newMember);
        newMember.setId(sequenceService.nextValueString());
        newMember.setSpaceId(defEntity.getId());
        newMember.setCreateUser(defEntity.getCreateUser());
        newMember.setCreateTime(new Date());
        if (!CollectionUtils.isEmpty(member.getOrgNames())){
            newMember.setOrgName(StringUtils.join(member.getOrgNames().toArray(),SEPARATOR));
        }
        if (!CollectionUtils.isEmpty(member.getUserRole())){
            newMember.setUserRole(StringUtils.join(member.getUserRole().toArray(),SEPARATOR));
        }
        if (!CollectionUtils.isEmpty(member.getDataAuthority())){
            newMember.setDataAuthority(StringUtils.join(member.getDataAuthority().toArray(),SEPARATOR));
        }
        if (!CollectionUtils.isEmpty(member.getFunctionalAuthority())){
            newMember.setFunctionalAuthority(StringUtils.join(member.getFunctionalAuthority().toArray(),SEPARATOR));
        }
        return newMember;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BusinessResult<Boolean> deleteById(IcreditWorkspaceDelParam param) {
        if (DEFAULT_WORKSPACEID.equals(param.getId())) {
            throw new AppException("80000002");
        }
        if (WorkspaceStatusEnum.ON.getCode().equals(getById(param.getId()).getStatus())) {
            throw new AppException("80000001");
        }
        //软删除该工作空间下的所有数据源及同步记录（暂且把数据源部分操作放在前面,保证事务性）
        BusinessResult<Boolean> result = datasourceFeignClient.delDatasourceFromWorkspace(param.getId());
        if (!result.isSuccess()) {
            throw new AppException(result.getReturnCode());
        }
        workspaceMapper.updateStatusById(param.getId());
        return BusinessResult.success(true);
    }

    //TODO:sql待优化
    @Override
    public BusinessPageResult queryPage(IcreditWorkspaceEntityPageRequest pageRequest) {
        IcreditWorkspaceEntityPageParam param = BeanCopyUtils.copyProperties(pageRequest, new IcreditWorkspaceEntityPageParam());
        Page<IcreditWorkspaceEntity> page = new Page<>(param.getPageNum(), param.getPageSize());
        BusinessResult<Boolean> result = systemFeignClient.isAdmin();
        //管理员，可以查询所有数据
        if (result.isSuccess() && Boolean.TRUE.equals(result.getData())) {
            log.info("当前用户为管理员，拥有全部空间权限");
            param.setUserId("");
        }
        if (!StringUtils.isBlank(pageRequest.getUpdateTime())) {
            param.setUpdateStartTime(DateUtils.parseDate(pageRequest.getUpdateTime() + " 00:00:00"));
            param.setUpdateEndTime(DateUtils.parseDate(pageRequest.getUpdateTime() + " 23:59:59"));
        }
        List<IcreditWorkspaceEntity> list = workspaceMapper.queryPage(page, param);
        //如果指定了空间，则不用展示默认工作空间
        if (!StringUtils.isBlank(pageRequest.getSpaceId()) && !DEFAULT_WORKSPACEID.equals(pageRequest.getSpaceId())){
            list = list.parallelStream()
                    .filter(w -> !DEFAULT_WORKSPACEID.equals(w.getId()))
                    .collect(Collectors.toList());
            page.setTotal(list.size());
        }
        return BusinessPageResult.build(page.setRecords(list), param);
    }

    @Override
    public BusinessResult<Boolean> hasExit(WorkspaceHasExistRequest request) {
        boolean hasExit = BooleanUtils.isTrue(workspaceMapper.hasExit(request));
        return BusinessResult.success(hasExit);
    }

    @Override
    public WorkspaceDetailResult getDetailById(String id) {
        if (DEFAULT_WORKSPACEID.equals(id)) {
            throw new AppException("80000003");
        }
        WorkspaceDetailResult result = new WorkspaceDetailResult();
        IcreditWorkspaceEntity entity = getById(id);
        if (null == entity) {
            return result;
        }
        BeanCopyUtils.copyProperties(entity, result);
        result.setCreateTime(Optional.ofNullable(entity.getCreateTime()).map(t -> t.getTime()).orElse(null));
        result.setUpdateTime(Optional.ofNullable(entity.getUpdateTime()).map(t -> t.getTime()).orElse(null));
        List<IcreditWorkspaceUserEntity> memberList = workspaceUserService.queryMemberListByWorkspaceId(id);
        List<WorkspaceMember> collect = memberList.stream().map(user -> {
            WorkspaceMember member = BeanCopyUtils.copyProperties(user, new WorkspaceMember());
            member.setCreateTime(user.getCreateTime().getTime());
            if (!StringUtils.isBlank(user.getOrgName())) {
                member.setOrgNames(Arrays.asList(user.getOrgName().split(SEPARATOR)));
            }
            if (!StringUtils.isBlank(user.getUserRole())) {
                member.setUserRole(Arrays.asList(user.getUserRole().split(SEPARATOR)));
            }
            if (!StringUtils.isBlank(user.getFunctionalAuthority())) {
                member.setFunctionalAuthority(Arrays.asList(user.getFunctionalAuthority().split(SEPARATOR)));
            }
            if (!StringUtils.isBlank(user.getDataAuthority())) {
                member.setDataAuthority(Arrays.asList(user.getDataAuthority().split(SEPARATOR)));
            }
            return member;
        }).collect(Collectors.toList());
        result.setMemberList(collect);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> updateWorkSpaceAndMember(IcreditWorkspaceUpdateParam param) {
        if (DEFAULT_WORKSPACEID.equals(param.getId())) {
            throw new AppException("80000004");
        }
        //更新workspace
        IcreditWorkspaceEntity entity = BeanCopyUtils.copyProperties(param, new IcreditWorkspaceEntity());
        entity.setUpdateTime(new Date());
        updateByspaceId(entity);
        if (CollectionUtils.isEmpty(param.getMemberList())) {
            return BusinessResult.success(true);
        }
        String spaceId = entity.getId();
        //先删除该空间下所有成员
        List<String> delList = workspaceUserService.queryMemberListByWorkspaceId(spaceId).stream().map(IcreditWorkspaceUserEntity::getId).collect(Collectors.toList());
        workspaceUserService.removeByIds(delList);
        for (int i = 0; i < param.getMemberList().size(); i++) {
            IcreditWorkspaceUserEntity newMember = getNewMember(param.getMemberList().get(i), entity);
            newMember.setSort(i);
            workspaceUserService.save(newMember);
        }
        return BusinessResult.success(true);
    }

    private void updateByspaceId(IcreditWorkspaceEntity entity) {
        //先停用该空间下的所有数据源，再停用该空间
        if (WorkspaceStatusEnum.OFF.getCode().equals(entity.getStatus())){
            BusinessResult<Boolean> result = datasourceFeignClient.offDatasourceFromWorkspace(entity.getId());
            if (!result.isSuccess()){
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_80000005.getCode());
            }
        }
        updateById(entity);
    }

    @Override
    public WorkBenchResult workbench(String userId, String id) {
        return schedulerFeign.workbench(userId, id);
    }
}
