package com.micro.cloud.modules.system.supplier.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.cloud.constant.SysErrorCodeConstants;
import com.micro.cloud.enums.OrgTypeEnum;
import com.micro.cloud.enums.SysCommonStatusEnum;
import com.micro.cloud.exception.ApiException;
import com.micro.cloud.modules.system.org.convert.SysOrgConvert;
import com.micro.cloud.modules.system.org.dataobject.SysOrg;
import com.micro.cloud.modules.system.org.mapper.SysOrgMapper;
import com.micro.cloud.modules.system.org.repository.SysOrgRepository;
import com.micro.cloud.modules.system.org.service.impl.SysOrgServiceImpl;
import com.micro.cloud.modules.system.org.vo.OrgUpdateStatusReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgCreateReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgPageReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgRespVO;
import com.micro.cloud.modules.system.supplier.entity.SysSupplierRef;
import com.micro.cloud.modules.system.supplier.mapper.SysSupplierMapper;
import com.micro.cloud.modules.system.supplier.service.SysSupplierService;
import com.micro.cloud.modules.system.supplier.vo.SysSupLowerVo;
import com.micro.cloud.modules.system.supplier.vo.SysSupPageReqVo;
import com.micro.cloud.modules.system.user.dataobject.SysUserOrgRef;
import com.micro.cloud.modules.system.user.mapper.SysUserMapper;
import com.micro.cloud.modules.system.user.mapper.SysUserOrgRefMapper;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import com.micro.cloud.snowflake.sequence.SequenceService;
import com.micro.cloud.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 供应商层级关系
 * @author: zlj
 * @create: 2021-12-21 2:29 下午
 */
@Service
public class SysSupplierServiceImpl extends ServiceImpl<SysSupplierMapper, SysSupplierRef> implements SysSupplierService {

    private Logger logger = LoggerFactory.getLogger(SysOrgServiceImpl.class);

    @Resource
    private SequenceService sequenceService;

    @Resource
    private SysOrgServiceImpl sysOrgService;

    @Resource
    private SysOrgRepository orgRepository;

    @Resource
    private SysSupplierMapper sysSupplierMapper;

    @Resource
    private SysOrgMapper sysOrgMapper;

    @Resource
    private SysUserOrgRefMapper userOrgRefMapper;

    @Resource
    private SysOrgMapper orgMapper;

    @Resource
    private SysUserMapper userMapper;



    @Override
    public String createSup(SysOrgCreateReqVO reqVO) {
        // 校验正确性
        sysOrgService.checkCreateOrUpdate(null, reqVO.getParentId(), reqVO.getTitle());
        //检验统一社会信用代码唯一
        sysOrgService.checkCreditCodeUnique(reqVO.getCreditCode(),null);
        //检验组织机构名称唯一
        sysOrgService.checkTitleUnique(reqVO.getTitle(),null);
        //校验手机号唯一
        sysOrgService.checkPhoneUnique(reqVO.getPhone(),null);
        // 插入部门
        SysOrg org = SysOrgConvert.INSTANCE.convertDO(reqVO);
        // 判断部门是否为叶子节点，创建时当上级部门不为空时则认为当前节点为叶子节点，同时需更新上级部门isLeaf为flase
        org.setSysOrgId(String.valueOf(sequenceService.nextValue(null)));
        // 系统组织机构
        org.setType(OrgTypeEnum.SUPPLIER.getValue());
        // 新增部门时默认为叶子结点
        org.setIsLeaf(true);
        org.setCreateTime(DateUtil.date());
        sysOrgService.save(org);
        // 更新上级部门isLeaf
        String parentId = reqVO.getParentId();
        orgRepository.upgradeDepartLevel(parentId);
        // 返回新增部门id
        return org.getSysOrgId();
    }

    @Override
    public IPage<SysOrgRespVO> page(SysOrgPageReqVO reqVO) {
        // 获取上级部门状态
        SysOrg parentOrg = sysOrgMapper.selectOne("sys_org_id", reqVO.getParentId());
        if (StringUtils.isNotBlank(reqVO.getCode())) {
            reqVO.setCode(Util.escapeStr(reqVO.getCode()));
        }
        if (StringUtils.isNotBlank(reqVO.getName())) {
            reqVO.setName(Util.escapeStr(reqVO.getName()));
        }
        Page<SysOrgRespVO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        IPage<SysOrgRespVO> pageResult = sysSupplierMapper.selectOrgList(page, reqVO);
        Optional.ofNullable(pageResult.getRecords())
                .ifPresent(
                        orgs -> {
                            orgs.stream()
                                    .filter(Objects::nonNull)
                                    .forEach(
                                            org ->
                                                    org.setParentStatus(
                                                            Objects.isNull(parentOrg) ? null : parentOrg.getStatus()));
                        });
        return page;
    }

    @Override
    public IPage<SysOrgRespVO> selLowerOrg(SysSupPageReqVo reqVO) {
        Page<SysOrgRespVO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
        IPage<SysOrgRespVO> pageResult = sysSupplierMapper.selLowerOrg(page, reqVO);
        return pageResult;
    }

    @Override
    public Integer setLowerSup(SysSupLowerVo vo) {
        //判断不能选自己为子部门
        if (vo.getParentId().equals(vo.getOrgId())) {
            throw new ApiException(SysErrorCodeConstants.ORG_EQ_PARENT);
        }
        //判断不能互选为子部门
        List<SysSupplierRef> refListParent = sysSupplierMapper.selectList(
                new QueryWrapperX<SysSupplierRef>().eq("sys_org_id", vo.getParentId()));
        List<SysSupplierRef> collect = refListParent.stream()
                .filter(s -> vo.getOrgId().equals(s.getParentId()))
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(collect)) {
            throw new ApiException(SysErrorCodeConstants.ORG_REJECTION_ERROR);
        }
        //判断不能重复设置子部门
        List<SysSupplierRef> refListSon = sysSupplierMapper.selectList(
                new QueryWrapperX<SysSupplierRef>().eq("parent_id", vo.getParentId()));
        List<SysSupplierRef> same = refListSon.stream()
                .filter(r -> r.getSysOrgId().equals(vo.getOrgId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(same)) {
            throw new ApiException(SysErrorCodeConstants.ORG_HAS_EXITS);
        }
        //供应商部门关联表插入一条记录
        SysSupplierRef ref = new SysSupplierRef();
        ref.setSysSupplierHierarchyRefId(sequenceService.nextStringValue(null));
        ref.setSysOrgId(vo.getOrgId());
        ref.setParentId(vo.getParentId());
        ref.setCreateTime(new Date());
        return sysSupplierMapper.insert(ref);
    }

    @Override
    public List<SysOrgRespVO> getAllSup() {
        return sysSupplierMapper.selectOrgAll();
    }

    @Override
    public void deleteDept(List<String> ids) {
        //查询所有下级部门
//        List<String> sons = sysSupplierMapper.selByParentIds(ids);
//        ids.addAll(sons);
        // 删除部门前需检测当前部门/下级部门是否存在用户，如果存在，不允许删除并返回提示
//        List<String> orgIds = sysOrgService.findChildren(ids);
        // 部门/子部门下是否存在用户
        Integer exists = userOrgRefMapper.existRefByOrgIds(ids);
        //删除部门需检测当前部门是否包含下级部门
//        if (exists > 0) {
//            throw new ApiException(SysErrorCodeConstants.ORG_HAS_USER);
//        }
        //删除当前部门
        boolean b = sysOrgService.removeByIds(ids);
        logger.info("=========删除当前部门{}", b);
        //解除上下级部门关系
        sysSupplierMapper.deleteByParentIds(ids);
        sysSupplierMapper.deleteByOrgIds(ids);
        logger.info("=======删除供应商下级部门关联表记录{}",ids);

        //需删除供应商部门关联表记录
//        sysSupplierMapper.deleteByOrgIds(ids);
//        sysSupplierMapper.deleteByParentIds(ids);
    }

    @Override
    public Integer deleteLowOrg(String id) {
        return sysSupplierMapper.deleteById(id);
    }

    /**
     * 批量更改部门状态(包含子部门及其用户)
     *
     * @param reqVO 部门id集合
     * @return 是否成功
     */
    @Override
    public Boolean changeStatusBatch(OrgUpdateStatusReqVO reqVO) {
        // 校验上级部门是否启用
        QueryWrapperX<SysOrg> orgQueryWrapperX = new QueryWrapperX<>();
        orgQueryWrapperX.inIfPresent("sys_org_id", reqVO.getIds());
        List<SysOrg> sysOrgs = orgMapper.selectList(orgQueryWrapperX);
        List<SysSupplierRef> parentIdList = sysSupplierMapper.selectList(
                new QueryWrapperX<SysSupplierRef>().eq("sys_org_id", reqVO.getIds()));
        Set<String> parentIds = parentIdList.stream().map(ref -> ref.getParentId()).collect(Collectors.toSet());
        Optional.ofNullable(sysOrgs)
                .ifPresent(
                        orgs -> {
//                            Set<String> parentIds =
//                                    orgs.stream().map(SysOrg::getParentId).collect(Collectors.toSet());

                            List<SysOrg> parentOrgs = new ArrayList<>();
                            if (CollectionUtils.isNotEmpty(parentIds)){
                                QueryWrapperX<SysOrg> parentWrapper = new QueryWrapperX<>();
                                parentWrapper.inIfPresent("sys_org_id", parentIds);
                                parentOrgs = orgMapper.selectList(parentWrapper);
                            }
//                            List<SysOrg> parentOrgs = orgMapper.selectList(parentWrapper);
                            if (CollectionUtil.isNotEmpty(parentOrgs)) {
                                Set<SysOrg> unableParents =
                                        parentOrgs.stream()
                                                .filter(
                                                        org -> SysCommonStatusEnum.DISABLE.getStatus().equals(org.getStatus()))
                                                .collect(Collectors.toSet());
                                if (CollectionUtil.isNotEmpty(unableParents)) {
                                    throw new ApiException(SysErrorCodeConstants.ORG_NOT_ENABLE);
                                }
                            }
                        });
        //   operateValidate.checkDeptEnable();
        // 获取所有下属部门
//        List<String> childrenIds =sysSupplierMapper.selByParentIds(reqVO.getIds());
//        childrenIds.addAll(reqVO.getIds());
        //todo 玻千院需求需要禁用部门只禁用当前部门和其下属用户
        List<String> orgIds=new ArrayList<>();
        orgIds.addAll(reqVO.getIds());
//        List<String> childrenIds = sysOrgService.findChildren(reqVO.getIds());
        // 获取所有下属部门人员id
        List<SysUserOrgRef> sysUserOrgRefs = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(orgIds)){
            QueryWrapperX<SysUserOrgRef> queryWrapperX = new QueryWrapperX<>();
            queryWrapperX.inIfPresent("sys_org_id", orgIds);
            sysUserOrgRefs = userOrgRefMapper.selectList(queryWrapperX);
        }
//        List<SysUserOrgRef> sysUserOrgRefs = userOrgRefMapper.selectList(queryWrapperX);
        // 更改所有下属用户状态
        if (CollectionUtils.isNotEmpty(sysUserOrgRefs)) {
            List<String> userIds =
                    sysUserOrgRefs.stream()
                            .map(SysUserOrgRef::getSysUserId)
                            .filter(Objects::nonNull)
                            .distinct()
                            .collect(Collectors.toList());
            userMapper.updateUserStatusBatch(userIds, reqVO.getStatus());
        }
        // 更改部门状态
        if (CollectionUtils.isNotEmpty(orgIds)) {
            orgMapper.updateStatusBatch(orgIds, reqVO.getStatus());
        }
        return true;
    }


}
