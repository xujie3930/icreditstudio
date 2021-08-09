package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hashtech.businessframework.exception.interval.AppException;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.Query;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.sequence.api.SequenceService;
import com.hashtech.businessframework.utils.excel.EasyExcelUtil;
import com.hashtech.businessframework.utils.excel.ExcelUtil;
import com.hashtech.businessframework.utils.sm4.SM4Utils;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import com.jinninghui.icreditdatasphere.icreditstudio.common.enums.DeleteFlagEnum;
import com.jinninghui.icreditdatasphere.icreditstudio.common.enums.NumberEnum;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.param.OrgTreeQueryParams;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.result.SelectInfoResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.allinterface.result.SelectTreeInfoResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.entity.OrganizationEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.entity.OrganizationEntityExpert;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.mapper.OrganizationDao;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.service.OrganizationService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.service.param.*;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.web.request.OrgChildrenQueryRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.web.request.OrgQueryRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.web.request.OrganizationEntityPageRequest;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.web.result.ExpertInfoResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.web.result.OrgQueryChildrenResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.web.result.OrganizationEntityResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.org.web.result.OrganizationInfoResult;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.entity.UserAccountEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.entity.UserOrgMapEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.UserAccountService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.UserOrgMapService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.param.UserAccountEntityConditionParam;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.SessionService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.TokenService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.uaa.service.result.PlatformUserAuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service("organizationService")
@Slf4j
public class OrganizationServiceImpl extends ServiceImpl<OrganizationDao, OrganizationEntity> implements OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;
    @Autowired
    private SequenceService generalSequence;
    @Autowired
    private UserOrgMapService userOrgMapService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private SessionService sessionService;
    @Value("${sm4.secretKey}")
    private String secretKey;

    @Override
    public BusinessPageResult queryPage(OrganizationEntityPageRequest pageRequest) {
        IPage<OrganizationEntity> page = this.page(
                new Query<OrganizationEntity>().getPage(pageRequest),
                new QueryWrapper<OrganizationEntity>()
        );

        return BusinessPageResult.build(page, pageRequest);
    }

    @Override
    public List<OrganizationInfoResult> getOrgInfoByUserId(OrganizationEntityQueryParam request) {

        return organizationDao.getOrgInfoByUserId(request);
    }

    @Override
    public List<SelectTreeInfoResult> getAllOrgTreeInfo(OrgTreeQueryParams params) {

        return organizationDao.getAllOrgTreeInfo(params);
    }

    @Override
    public List<SelectInfoResult> getAllOrgInfo() {

        return organizationDao.getAllOrgInfo();
    }

    @Override
    public BusinessResult<?> exportExcel(HttpServletRequest request, HttpServletResponse response, OrganizationEntity organization, String userId) {

        try {
            OrganizationEntityQueryParam param = new OrganizationEntityQueryParam();
            param.setAccessUserId(userId);
            BeanUtils.copyProperties(organization, param);
            List<OrganizationEntityResult> data = listQuery(param).getData();
            List<OrganizationEntityExpert> list = new ArrayList<>();
            for (OrganizationEntityResult datum : data) {
                OrganizationEntityExpert organizationEntityExpert = new OrganizationEntityExpert();
                BeanUtils.copyProperties(datum, organizationEntityExpert);
                list.add(organizationEntityExpert);
            }
            Map<String, String> orgMap = new HashMap<>();
            for (OrganizationEntityExpert org : list) {
                orgMap.put(org.getId(), org.getOrgName());
            }
            for (OrganizationEntityExpert org : list) {
                String name = orgMap.get(org.getParentId());
                if (null != name) {
                    org.setParentName(name);
                }
            }
            ExcelUtil.exportExcel(response, "部门列表导出", list, OrganizationEntityExpert.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return BusinessResult.fail("", "部门列表导出失败");
        }
        return BusinessResult.success("部门列表导出成功");
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response, Class<OrganizationEntityExpert> organizationEntityExpertClass) {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        Map<String, String> orgNameAndId = new HashMap<>();
        List<String> codeList = new ArrayList<>();
        ExpertInfoResult expertInfoResult = new ExpertInfoResult();
        List<OrganizationEntity> passList = new ArrayList<>();
        List<Object> noPassList = new ArrayList<>();

        // 获取所有的部门信息
        List<OrganizationEntity> organizationEntityList = this.list();
        if (CollectionUtils.isNotEmpty(organizationEntityList)) {
            orgNameAndId = organizationEntityList.stream()
                    .collect(Collectors.toMap(OrganizationEntity::getOrgName, OrganizationEntity::getId, (k1, k2) -> k1));
            codeList =
                    organizationEntityList.stream().map(OrganizationEntity::getOrgCode).collect(Collectors.toList());
        }

        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 获取上传文件对象
            MultipartFile file = entity.getValue();
            try {
                List<T> importExcelList = EasyExcelUtil.syncReadModel(file.getInputStream(), organizationEntityExpertClass, 0, 1);
                List<OrganizationEntityExpert> infoExperts = BeanCopyUtils.copy(importExcelList, OrganizationEntityExpert.class);

                // 去除导入时 组织名称的重复
                Map<String, List<OrganizationEntityExpert>> orgNameGroupBy = infoExperts
                        .stream().filter(f -> f.getOrgName() != null)
                        .collect(Collectors.groupingBy(OrganizationEntityExpert::getOrgName));
                orgNameGroupBy.forEach((key, value) -> {
                    if (value.size() > 1) {
                        value.forEach(f -> {
                            String format = String.format("导入组织名称[%S]重复!", f.getOrgName());
                            f.setErrorMsg(format);
                            noPassList.add(f);
                        });
                        infoExperts.removeAll(value);
                    }
                });

                // 去除导入时 组织编码的重复
                Map<String, List<OrganizationEntityExpert>> interfacesCodeGroupBy = infoExperts
                        .stream().filter(f -> f.getOrgCode() != null)
                        .collect(Collectors.groupingBy(OrganizationEntityExpert::getOrgCode));
                interfacesCodeGroupBy.forEach((key, value) -> {
                    if (value.size() > 1) {
                        value.forEach(f -> {
                            String format = String.format("导入组织编码[%S]重复!", f.getOrgCode());
                            f.setErrorMsg(format);
                            noPassList.add(f);
                        });
                        infoExperts.removeAll(value);
                    }
                });

                for (OrganizationEntityExpert organizationEntityExpert : infoExperts) {
                    //校验
                    if (!importOrgCheck(orgNameAndId, codeList, noPassList, organizationEntityExpert)) {
                        continue;
                    }
                    // 通过保存
                    OrganizationEntity organizationEntity = BeanCopyUtils.copyProperties(organizationEntityExpert, OrganizationEntity.class);
                    organizationEntity.setId(generalSequence.nextValueString());
                    // 父部门 赋值
//                    organizationEntity.setParentId(orgNameAndId.get(organizationEntityExpert.getParentName()));
                    passList.add(organizationEntity);
                }
                expertInfoResult.setNoPassList(noPassList);
                expertInfoResult.setErrorCount(noPassList.size());
                expertInfoResult.setSuccessCount(passList.size());

                this.saveBatch(passList);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                //手动开启事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return BusinessResult.fail("00", "文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return BusinessResult.success(expertInfoResult);
    }

    @Override
    public List<OrganizationEntity> getOrgInfoByParams(OrgQueryRequest request) {

        return organizationDao.selectList(new QueryWrapper<OrganizationEntity>()
                .eq(request.getOrgId() != null, "id", request.getOrgId())
                .eq(request.getOrgCode() != null, "org_code", request.getOrgCode())
                .eq(request.getDeleteFlag() != null, "delete_flag", request.getDeleteFlag())
                .like(request.getOrgName() != null, "org_name", request.getOrgName())

        );
    }

    @Override
    public List<OrganizationEntity> getChildrenOrgInfoByParams(OrgChildrenQueryRequest request) {

        return organizationDao.getChildrenOrgInfoByParams(request);
    }

    @Override
    public List<OrgQueryChildrenResult> getOrgTreeInfoByParams(OrgChildrenQueryRequest request) {
        List<OrganizationEntity> organizationEntityList;
        // 传条件 查询当前组织下的子部门 不传条件 查询所有部门信息
        if (StringUtils.isNotEmpty(request.getOrgId()) || StringUtils.isNotEmpty(request.getOrgCode())) {
            organizationEntityList = organizationDao.selectList(new QueryWrapper<OrganizationEntity>()
                    .eq(request.getOrgId() != null, "id", request.getOrgId())
                    .eq(request.getOrgCode() != null, "org_code", request.getOrgCode())
                    .eq(request.getDeleteFlag() != null, "delete_flag", request.getDeleteFlag())
            );
        } else {
            organizationEntityList = organizationDao.selectList(new QueryWrapper<OrganizationEntity>()
                    .eq("parent_id", null)
                    .eq(request.getDeleteFlag() != null, "delete_flag", request.getDeleteFlag())
            );
        }
        List<OrgQueryChildrenResult> results = BeanCopyUtils.copy(organizationEntityList, OrgQueryChildrenResult.class);

        // 获取子部门信息
        getChildrenOrgInfo(request, results);

        return results;
    }

    private void getChildrenOrgInfo(OrgChildrenQueryRequest request, List<OrgQueryChildrenResult> results) {
        if (CollectionUtils.isNotEmpty(results)) {
            for (OrgQueryChildrenResult queryChildrenResult : results) {
                List<OrganizationEntity> organizationEntityList1 = organizationDao.selectList(new QueryWrapper<OrganizationEntity>()
                        .eq("parent_id", queryChildrenResult.getId())
                        .eq(request.getDeleteFlag() != null, "delete_flag", request.getDeleteFlag())
                );

                if (CollectionUtils.isNotEmpty(organizationEntityList1)) {
                    queryChildrenResult.setChildren(organizationEntityList1);
                    List<OrgQueryChildrenResult> results1 = BeanCopyUtils.copy(organizationEntityList1, OrgQueryChildrenResult.class);
                    getChildrenOrgInfo(request, results1);
                }

            }
        }
    }

    private boolean importOrgCheck(Map<String, String> orgNameAndId, List<String> codeList, List<Object> noPassList,
                                   OrganizationEntityExpert organizationEntityExpert) {
        //校验部门名称不能为空
        if (StringUtils.isEmpty(organizationEntityExpert.getOrgName())) {
            organizationEntityExpert.setErrorMsg("部门名称不能为空");
            noPassList.add(organizationEntityExpert);
            return false;
        }
        //校验部门名称是否已存在
        if (orgNameAndId.get(organizationEntityExpert.getOrgName()) != null) {
            String format = String.format("部门名称[%S]已存在!", organizationEntityExpert.getOrgName());
            organizationEntityExpert.setErrorMsg(format);
            noPassList.add(organizationEntityExpert);
            return false;
        }
        //校验部门编码是否已存在
        if (codeList.contains(organizationEntityExpert.getOrgCode())) {
            String format = String.format("部门编码[%S]已存在!", organizationEntityExpert.getOrgCode());
            organizationEntityExpert.setErrorMsg(format);
            noPassList.add(organizationEntityExpert);
            return false;
        }
        return true;
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<Boolean> save(OrganizationEntitySaveParam param) {
        List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().build());
        List<UserOrgMapEntity> userOrgMaps = getUserOrgMaps(Sets.newHashSet(param.getAccessUserId()), Sets.newHashSet());
        //合法性检查
        operationalLegalityCheck(param.getAccessUserId(), Sets.newHashSet(param.getParentId()), userOrgMaps, organizations);
        String orgCode = StringUtils.trim(param.getOrgCode());
        //编码唯一性检查
        codingUniquenessCheck(orgCode, organizations, true, null);
        String orgName = StringUtils.trim(param.getOrgName());
        //名称唯一性校验
        nameUniquenessCheck(orgName, param.getParentId(), organizations, true);
        OrganizationEntity entity = new OrganizationEntity();
        BeanCopyUtils.copyProperties(param, entity);
        if (StringUtils.isNotBlank(param.getRemark())) {
            entity.setOrgRemark(param.getRemark());
        }
        return BusinessResult.success(saveOrUpdate(entity));
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<OrganizationEntityResult> edit(OrganizationEntitySaveParam param) {
        List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().build());
        List<UserOrgMapEntity> userOrgMaps = getUserOrgMaps(Sets.newHashSet(param.getAccessUserId()), Sets.newHashSet());
        //操作合法性检查
        operationalLegalityCheck(param.getAccessUserId(), Sets.newHashSet(param.getId()), userOrgMaps, organizations);
        //编码唯一性检查
        codingUniquenessCheck(param.getOrgCode(), organizations, false, param.getId());
        //名称唯一性校验
        nameUniquenessCheck(param.getOrgName(), param.getId(), organizations, false);
        //父级部门校验
        parentOrganizationCheck(param.getId(), param.getParentId(), organizations);
        List<OrganizationEntity> oldEntity = getOrganizations(OrganizationEntityConditionParam.builder().ids(Sets.newHashSet(param.getId())).build());

        OrganizationEntity entity = new OrganizationEntity();
        BeanCopyUtils.copyProperties(param, entity);
        entity.setOrgRemark(param.getRemark());
        saveOrUpdate(entity);
        OrganizationEntityResult organizationEntityResult = BeanCopyUtils.copyProperties(oldEntity.get(0), OrganizationEntityResult.class);
        return BusinessResult.success(organizationEntityResult);
    }

    private void parentOrganizationCheck(String currOrgId, String parentOrgId, List<OrganizationEntity> organizations) {
        if (StringUtils.isNotBlank(currOrgId) && StringUtils.isNotBlank(parentOrgId) && CollectionUtils.isNotEmpty(organizations)) {
            Set<String> currAndSonOrgIds = findCurrAndSonOrgIds(Sets.newHashSet(currOrgId), organizations);
            if (currAndSonOrgIds.contains(parentOrgId)) {
                throw new AppException("50009341");
            }
        }
    }

    private void codingUniquenessCheck(String code, List<OrganizationEntity> organizations, boolean includeSelf, String selfOrgId) {
        if (StringUtils.isNotBlank(code) && CollectionUtils.isNotEmpty(organizations)) {
            long count = organizations.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(entity -> {
                        boolean flag = true;
                        if (!includeSelf) {
                            flag = !selfOrgId.equals(entity.getId());
                        }
                        return flag;
                    })
                    .filter(entity -> code.equals(entity.getOrgCode()))
                    .count();
            if (count > 0) {
                throw new AppException("50009340");
            }
        }
    }

    private void nameUniquenessCheck(String name, String operateOrgId, List<OrganizationEntity> organizations, boolean includeSelf) {
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(operateOrgId) && CollectionUtils.isNotEmpty(organizations)) {
            Set<String> sameLevelOrgIds = findSameLevelOrgIds(operateOrgId, organizations);
            Set<String> directSubOrgIds = findDirectSubOrgIds(operateOrgId, organizations);
            HashSet<String> orgIds = Sets.newHashSet(sameLevelOrgIds);
            orgIds.addAll(directSubOrgIds);

            long count = organizations.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(entity -> {
                        boolean flag = true;
                        if (!includeSelf) {
                            flag = !operateOrgId.equals(entity.getId());
                        }
                        return flag;
                    })
                    .filter(entity -> orgIds.contains(entity.getId()))
                    .filter(entity -> name.equals(entity.getOrgName()))
                    .count();
            if (count > 0) {
                throw new AppException("50009336");
            }
        }

    }

    @Override
    public List<OrganizationEntity> getOrgByIdsAndDelFlag(Set<String> ids, DeleteFlagEnum deleteFlag) {
        List<OrganizationEntity> result = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(ids) && deleteFlag != null) {
            QueryWrapper<OrganizationEntity> wrapper = new QueryWrapper<>();
            wrapper.in(OrganizationEntity.ID, ids);
            if (!DeleteFlagEnum.ALL.getCode().equals(deleteFlag.getCode())) {
                wrapper.eq(OrganizationEntity.DELETE_FLAG, deleteFlag.getCode());
            }
            result = organizationDao.selectList(wrapper);
        }
        //返回符合条件的org列表
        return result;
    }

    @Override
    public Set<String> filterIdsByDelFlag(Set<String> ids, DeleteFlagEnum deleteFlag) {
        Set<String> result = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(ids) && deleteFlag != null) {
            //deleteFlag为all则不通过deleteFlag字段过滤,返回原列表的去重集合
            if (DeleteFlagEnum.ALL.getCode().equals(deleteFlag.getCode())) {
                result = ids;
            } else {
                //通过deleteFlag过滤,取得符合条件的org列表
                List<OrganizationEntity> orgs = getOrgByIdsAndDelFlag(ids, deleteFlag);
                result = Optional.ofNullable(orgs).orElse(Lists.newArrayList())
                        .parallelStream()
                        .filter(org -> Objects.nonNull(org))
                        .map(OrganizationEntity::getId)
                        .collect(Collectors.toSet());
            }
        }
        return result;
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> status(OrganizationEntityStatusParam param) {
        List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().build());
        List<UserOrgMapEntity> userOrgMaps = getUserOrgMaps(Sets.newHashSet(param.getAccessUserId()), Sets.newHashSet());
        //操作合法性检查
        operationalLegalityCheck(param.getAccessUserId(), Sets.newHashSet(param.getId()), userOrgMaps, organizations);
        Set<String> currAndSonOrgIds = findCurrAndSonOrgIds(Sets.newHashSet(param.getId()), organizations);
        List<OrganizationEntity> entities = currAndSonOrgIds.parallelStream()
                .filter(StringUtils::isNoneBlank)
                .map(id -> {
                    OrganizationEntity entity = new OrganizationEntity();
                    entity.setId(id);
                    entity.setDeleteFlag(param.getDeleteFlag());
                    return entity;
                }).collect(Collectors.toList());
        /*if (DeleteFlagEnum.Y.getCode().equals(param.getDeleteFlag())) {
            List<UserOrgMapEntity> userOrgMapEntities = getUserOrgMaps(Sets.newHashSet(), currAndSonOrgIds);
            Set<String> userIdsByOrgIds = findUserIdsByOrgIds(currAndSonOrgIds, userOrgMapEntities);
            List<UserAccountEntity> userAccounts = getUserAccounts(UserAccountEntityConditionParam.builder().userIds(userIdsByOrgIds).build());
            //删除token
            deleteAccountToken(userAccounts);
        }*/
        return BusinessResult.success(updateBatchById(entities));
    }

    private Set<String> findUserIdsByOrgIds(Set<String> orgIds, List<UserOrgMapEntity> userOrgMaps) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(orgIds) && CollectionUtils.isNotEmpty(userOrgMaps)) {
            results = userOrgMaps.parallelStream()
                    .filter(Objects::nonNull)
                    .map(UserOrgMapEntity::getUserId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    private void deleteAccountToken(List<UserAccountEntity> accounts) {
        if (CollectionUtils.isNotEmpty(accounts)) {
            accounts.parallelStream()
                    .filter(Objects::nonNull)
                    .forEach(account -> {
                        SM4Utils sm4 = new SM4Utils(secretKey);
                        String passWord = sm4.decryptData_ECB(account.getAccountCredential());
                        BusinessResult<PlatformUserAuthResponse> token = sessionService.getToken(account.getAccountIdentifier(), passWord);
                        PlatformUserAuthResponse data = token.getData();
                        if (token.isSuccess() && Objects.nonNull(data)) {
                            tokenService.removeToken(data.getAccessToken());
                        }
                    });
        }
    }

    @Override
    public BusinessResult<List<OrganizationEntityResult>> delete(OrgEntityDelParam param) {
        List<UserOrgMapEntity> userOrgMaps = getUserOrgMaps(Sets.newHashSet(), Sets.newHashSet());
        List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam.builder().build());
        //操作合法性检查
        operationalLegalityCheck(param.getAccessUserId(), param.getIds(), userOrgMaps, organizations);
        //有子部门不能删
        if (CollectionUtils.isNotEmpty(loopFindSonOrgIds(param.getIds(), organizations))) {
            throw new AppException("50009342");
        }
        //部门下有用户不能删
        if (CollectionUtils.isNotEmpty(findUserOrgMapsByOrgIds(param.getIds(), userOrgMaps))) {
            throw new AppException("50009343");
        }
        List<OrganizationEntity> orgByIds = findOrgByIds(param.getIds(), organizations);
        long count = orgByIds.parallelStream()
                .filter(organizationEntity -> DeleteFlagEnum.N.getCode().equals(organizationEntity.getDeleteFlag()))
                .count();
        if (count > 0) {
            throw new AppException("50009344");
        }
        removeByIds(param.getIds());
        List<OrganizationEntityResult> copy = BeanCopyUtils.copy(orgByIds, OrganizationEntityResult.class);
        return BusinessResult.success(copy);
    }

    @Override
    public List<OrganizationEntity> getOrganizationByUserId(String userId) {
        List<UserOrgMapEntity> userOrgMaps = getUserOrgMaps(Sets.newHashSet(userId), Sets.newHashSet());
        Set<String> orgIdsFromUserOrgMaps = findOrgIdsFromUserOrgMaps(userOrgMaps);
        return getOrganizations(OrganizationEntityConditionParam.builder().ids(orgIdsFromUserOrgMaps).build());
    }

    private Set<String> findOrgIdsFromUserOrgMaps(List<UserOrgMapEntity> userOrgMaps) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(userOrgMaps)) {
            results = userOrgMaps.parallelStream()
                    .filter(Objects::nonNull)
                    .map(UserOrgMapEntity::getOrgId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<List<OrganizationEntityResult>> listQuery(OrganizationEntityQueryParam param) {
        List<OrganizationEntityResult> results = Lists.newArrayList();
        List<OrganizationEntity> organizations = getOrganizations(OrganizationEntityConditionParam
                .builder().deleteFlag(param.getDeleteFlag()).build());
        List<UserOrgMapEntity> userOrgMaps = getUserOrgMaps(Sets.newHashSet(param.getAccessUserId()), Sets.newHashSet());
        //当前部门
        Set<String> currOrgIds = findCurrOrgIds(Sets.newHashSet(param.getAccessUserId()), userOrgMaps);
        Set<String> currAndSonOrgIds = findCurrAndSonOrgIds(currOrgIds, organizations);

        results = getOrganizationLink(param.getAccessUserId(), organizations, userOrgMaps);
//        results = findByIds(results, currAndSonOrgIds);
        if (StringUtils.isNotBlank(param.getLinkManName()) || StringUtils.isNotBlank(param.getOrgName())) {
            List<OrganizationEntity> conditionOrgs = getOrganizations(OrganizationEntityConditionParam.builder()
                    .orgName(param.getOrgName())
                    .linkManName(param.getLinkManName()).build());
            Set<String> orgIds = findOrgIds(conditionOrgs);
            Set<String> currAndParentOrgIds = findCurrAndParentOrgIds(organizations, orgIds);
            results = findByIds(results, currAndParentOrgIds);
        }
        results = sort(results);
        return BusinessResult.success(results);
    }

    private void operationalLegalityCheck(String accessUserId, Set<String> operateOrgIds, List<UserOrgMapEntity> userOrgMaps, List<OrganizationEntity> organizations) {
        Set<String> currAndSonOrgIds = findCurrAndSonOrgIdsByUserId(accessUserId, userOrgMaps, organizations);
        if (!currAndSonOrgIds.containsAll(operateOrgIds)) {
            throw new AppException("50009115");
        }
    }

    private Set<String> findSameLevelOrgIds(String currOrgId, List<OrganizationEntity> organizations) {
        Set<String> results = Sets.newHashSet();
        List<OrganizationEntityResult> copy = BeanCopyUtils.copy(organizations, OrganizationEntityResult.class);
        List<OrganizationEntityResult> byIds = findByIds(copy, Sets.newHashSet(currOrgId));
        if (CollectionUtils.isNotEmpty(byIds)) {
            OrganizationEntityResult entityResult = byIds.get(0);
            String parentId = entityResult.getParentId();
            results = findDirectSubOrgIds(parentId, organizations);
            results.remove(currOrgId);
        }
        return results;
    }

    private List<OrganizationEntity> findOrgByIds(Set<String> orgIds, List<OrganizationEntity> organizations) {
        List<OrganizationEntity> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(orgIds) && CollectionUtils.isNotEmpty(organizations)) {
            results = organizations.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(organizationEntity -> orgIds.contains(organizationEntity.getId()))
                    .collect(Collectors.toList());
        }
        return results;
    }

    private Set<String> findDirectSubOrgIds(String currOrgId, List<OrganizationEntity> organizations) {
        Set<String> results = Sets.newHashSet();
        if (StringUtils.isNotBlank(currOrgId) && CollectionUtils.isNotEmpty(organizations)) {
            results = organizations.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(entity -> currOrgId.equals(entity.getParentId()))
                    .map(OrganizationEntity::getId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    private Set<String> findCurrAndSonOrgIdsByUserId(String userId, List<UserOrgMapEntity> userOrgMaps, List<OrganizationEntity> organizations) {
        Set<String> currOrgIds = findCurrOrgIds(Sets.newHashSet(userId), userOrgMaps);
        Set<String> currAndSonOrgIds = findCurrAndSonOrgIds(currOrgIds, organizations);
        return Sets.newHashSet(currAndSonOrgIds);
    }

    private List<OrganizationEntityResult> getOrganizationLink(String userId, List<OrganizationEntity> organizations, List<UserOrgMapEntity> userOrgMaps) {
        List<OrganizationEntityResult> results = Lists.newArrayList();
        Set<String> currOrgIds = findCurrOrgIds(Sets.newHashSet(userId), userOrgMaps);
        Set<String> currAndSonOrgIds = findCurrAndSonOrgIds(currOrgIds, organizations);
        if (CollectionUtils.isNotEmpty(organizations)) {
            results = organizations.parallelStream()
                    .filter(Objects::nonNull)
                    .map(organization -> {
                        OrganizationEntityResult result = new OrganizationEntityResult();
                        BeanCopyUtils.copyProperties(organization, result);
                        if (currAndSonOrgIds.contains(organization.getId())) {
                            result.setOperateFlag(NumberEnum.ONE.getNum());
                        }
                        if (currOrgIds.contains(organization.getId())) {
                            result.setCurrOrg(true);
                        }
                        return result;
                    }).collect(Collectors.toList());

        }
        return results;
    }

    private Set<String> findOrgIds(List<OrganizationEntity> entities) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(entities)) {
            results = entities.parallelStream()
                    .filter(Objects::nonNull)
                    .map(OrganizationEntity::getId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    private List<UserOrgMapEntity> findUserOrgMapsByOrgIds(Set<String> orgIds, List<UserOrgMapEntity> userOrgMaps) {
        List<UserOrgMapEntity> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(orgIds) && CollectionUtils.isNotEmpty(userOrgMaps)) {
            results = userOrgMaps.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(userOrgMapEntity -> orgIds.contains(userOrgMapEntity.getOrgId()))
                    .collect(Collectors.toList());
        }
        return results;
    }

    private List<OrganizationEntityResult> findByIds(List<OrganizationEntityResult> orgs, Set<String> ids) {
        return orgs.parallelStream()
                .filter(Objects::nonNull)
                .filter(organizationEntity -> ids.contains(organizationEntity.getId()))
                .collect(Collectors.toList());
    }

    private List<OrganizationEntityResult> sort(List<OrganizationEntityResult> entityResults) {
        return entityResults.stream()
                .filter(Objects::nonNull)
                .map(result -> {
                    if (Objects.isNull(result.getSortNumber())) {
                        result.setSortNumber(0);
                    }
                    return result;
                })
                .sorted(Comparator.comparing(OrganizationEntityResult::getSortNumber)
                        .thenComparing(OrganizationEntityResult::getCreateTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private Set<String> findCurrOrgIds(Set<String> userIds, List<UserOrgMapEntity> userOrgMaps) {
        Set<String> results = Sets.newHashSet();
        results = userOrgMaps.parallelStream()
                .filter(Objects::nonNull)
                .filter(entity -> userIds.contains(entity.getUserId()))
                .map(UserOrgMapEntity::getOrgId)
                .collect(Collectors.toSet());
        return results;
    }

    private Set<String> findCurrAndSonOrgIds(Set<String> currOrgIds, List<OrganizationEntity> orgs) {
        Set<String> sonOrgIds = loopFindSonOrgIds(currOrgIds, orgs);
        Set<String> results = Sets.newHashSet(sonOrgIds);
        results.addAll(currOrgIds);
        return results;
    }

    /**
     * 当前部门子部门id
     */
    @SuppressWarnings("all")
    private Set<String> loopFindSonOrgIds(Set<String> currOrgIds, List<OrganizationEntity> orgs) {
        Set<String> result = Sets.newHashSet();
        Set<String> temp = currOrgIds;
        while (CollectionUtils.isNotEmpty(temp)) {
            Set<String> tempChildOrgIds = Sets.newHashSet();
            for (String orgId : temp) {
                Set<String> childOrgIds = Optional.ofNullable(orgs).orElse(Lists.newArrayList())
                        .parallelStream()
                        .filter(org -> orgId.equals(org.getParentId()))
                        .map(OrganizationEntity::getId)
                        .collect(Collectors.toSet());
                result.addAll(childOrgIds);
                tempChildOrgIds.addAll(childOrgIds);
            }
            temp = tempChildOrgIds;
        }
        return result;
    }

    private Set<String> findCurrAndParentOrgIds(List<OrganizationEntity> orgs, Set<String> currOrgIds) {
        Set<String> parentOrgIds = loopFindParentOrgIds(currOrgIds, orgs);
        Set<String> results = Sets.newHashSet(parentOrgIds);
        results.addAll(currOrgIds);
        return results;
    }

    @SuppressWarnings("all")
    private Set<String> loopFindParentOrgIds(Set<String> currOrgIds, List<OrganizationEntity> orgs) {
        Set<String> results = Sets.newHashSet();
        Set<String> temp = currOrgIds;
        while (CollectionUtils.isNotEmpty(temp)) {
            Set<String> tempParentOrgIds = Sets.newHashSet();
            for (String orgId : temp) {
                Set<String> parentOrgIds = Optional.ofNullable(orgs).orElse(Lists.newArrayList())
                        .parallelStream()
                        .filter(org -> orgId.equals(org.getId()))
                        .map(OrganizationEntity::getParentId)
                        .collect(Collectors.toSet());
                results.addAll(parentOrgIds);
                tempParentOrgIds.addAll(parentOrgIds);
            }
            temp = tempParentOrgIds;
        }
        return results;
    }

    private List<UserAccountEntity> getUserAccounts(UserAccountEntityConditionParam param) {
        QueryWrapper<UserAccountEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getUserIds())) {
            wrapper.in(UserAccountEntity.USER_ID, param.getUserIds());
        }
        return userAccountService.list(wrapper);
    }

    /**
     * 查询部门
     *
     * @return
     */
    private List<OrganizationEntity> getOrganizations(OrganizationEntityConditionParam param) {
        QueryWrapper<OrganizationEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(OrganizationEntity.ID, param.getIds());
        }
        if (StringUtils.isNotBlank(param.getOrgName())) {
            wrapper.like(OrganizationEntity.ORG_NAME, param.getOrgName());
        }
        if (StringUtils.isNotBlank(param.getLinkManName())) {
            wrapper.like(OrganizationEntity.LINK_MAN_NAME, param.getLinkManName());
        }
        if (StringUtils.isNotBlank(param.getDeleteFlag())) {
            wrapper.eq(OrganizationEntity.DELETE_FLAG, param.getDeleteFlag());
        }
        return list(wrapper);
    }

    /**
     * 查询用户所在部门
     *
     * @param userIds
     * @param orgIds
     * @return
     */
    private List<UserOrgMapEntity> getUserOrgMaps(Set<String> userIds, Set<String> orgIds) {
        QueryWrapper<UserOrgMapEntity> wrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(userIds)) {
            wrapper.in(UserOrgMapEntity.USER_ID, userIds);
        }
        if (CollectionUtils.isNotEmpty(orgIds)) {
            wrapper.in(UserOrgMapEntity.ORG_ID, orgIds);
        }
        return userOrgMapService.list(wrapper);
    }
}
