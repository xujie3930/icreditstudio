package com.micro.cloud.modules.system.org.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.cloud.constant.SysErrorCodeConstants;
import com.micro.cloud.enums.OrgTypeEnum;
import com.micro.cloud.enums.SysCommonStatusEnum;
import com.micro.cloud.exception.ApiException;
import com.micro.cloud.modules.system.org.convert.SysOrgConvert;
import com.micro.cloud.modules.system.org.dataobject.SysOrg;
import com.micro.cloud.modules.system.org.mapper.SysOrgMapper;
import com.micro.cloud.modules.system.org.param.OrgUserParam;
import com.micro.cloud.modules.system.org.repository.SysOrgRepository;
import com.micro.cloud.modules.system.org.service.SysOrgService;
import com.micro.cloud.modules.system.org.vo.*;
import com.micro.cloud.modules.system.user.dataobject.SysUserOrgRef;
import com.micro.cloud.modules.system.user.mapper.SysUserMapper;
import com.micro.cloud.modules.system.user.mapper.SysUserOrgRefMapper;
import com.micro.cloud.modules.system.user.validate.UserCommonOperateValidate;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserSimpleVO;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import com.micro.cloud.snowflake.sequence.SequenceService;
import com.micro.cloud.util.Util;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Service
public class SysOrgServiceImpl extends ServiceImpl<SysOrgMapper, SysOrg> implements SysOrgService {

  private Logger logger = LoggerFactory.getLogger(SysOrgServiceImpl.class);

  @Autowired private SequenceService sequenceService;

  @Autowired private UserCommonOperateValidate operateValidate;

  @Autowired private SysOrgRepository orgRepository;

  @Autowired private SysOrgMapper orgMapper;

  @Autowired private SysUserOrgRefMapper userOrgRefMapper;

  @Autowired private SysUserMapper userMapper;

  /** 初始化部门缓存 */
  @Override
  public void initCache() {}

  /**
   * 创建部门
   *
   * @param reqVO 部门信息
   * @return 部门编号
   */
  @Override
  public String createDept(SysOrgCreateReqVO reqVO) {
    // 校验正确性
    checkCreateOrUpdate(null, reqVO.getParentId(), reqVO.getTitle());
    //检验统一社会信用代码唯一
    if (StringUtils.isNotBlank(reqVO.getCreditCode())){
      checkCreditCodeUnique(reqVO.getCreditCode(),null);
    }
    //检验组织机构名称唯一
    checkTitleUnique(reqVO.getTitle(),null);
    //校验手机号唯一
    checkPhoneUnique(reqVO.getPhone(),null);
    // 插入部门
    SysOrg org = SysOrgConvert.INSTANCE.convertDO(reqVO);
    // 判断部门是否为叶子节点，创建时当上级部门不为空时则认为当前节点为叶子节点，同时需更新上级部门isLeaf为flase
    org.setSysOrgId(String.valueOf(sequenceService.nextValue(null)));
    // 系统组织机构
    org.setType(OrgTypeEnum.INTERNAL.getValue());
    // 新增部门时默认为叶子结点
    org.setIsLeaf(true);
    org.setCreateTime(DateUtil.date());
    super.save(org);
    // 更新上级部门isLeaf
    String parentId = reqVO.getParentId();
    orgRepository.upgradeDepartLevel(parentId);
    // 返回新增部门id
    return org.getSysOrgId();
  }

  /**
   * 更新部门
   *
   * @param reqVO 部门信息
   */
  @Override
  public Boolean updateDept(SysOrgUpdateReqVO reqVO) {
    // 校验正确性
    checkCreateOrUpdate(reqVO.getId(), reqVO.getParentId(), reqVO.getTitle());
    //检验统一社会信用代码唯一
    if (StringUtils.isNotBlank(reqVO.getCreditCode())){
      checkCreditCodeUnique(reqVO.getCreditCode(),reqVO.getId());
    }
    //检验组织机构名称唯一
    checkTitleUnique(reqVO.getTitle(),reqVO.getId());
    //校验手机号唯一
    checkPhoneUnique(reqVO.getPhone(),reqVO.getId());
    // 检测部门状态是否发生变更
    SysOrg original = orgMapper.selectById(reqVO.getId());
    if (ObjectUtil.notEqual(original.getStatus(), reqVO.getStatus())) {
      OrgUpdateStatusReqVO updateStatusReqVO = new OrgUpdateStatusReqVO();
      updateStatusReqVO.setIds(Collections.singletonList(reqVO.getId()));
      updateStatusReqVO.setStatus(reqVO.getStatus());
      changeStatusBatch(updateStatusReqVO);
    }

    // 更新部门
    SysOrg updateObj = SysOrgConvert.INSTANCE.convertDO(reqVO);

    orgMapper.updateById(updateObj);
    return true;
  }

  /**
   * 删除部门
   *
   * @param orgId 部门id
   */
  @Override
  public void deleteDept(String orgId) {
    // 删除部门前需检测当前部门/下级部门是否存在用户，如果存在，不允许删除并返回提示
    List<String> orgIds = findChildren(Collections.singletonList(orgId));
    // 部门/子部门下是否存在用户
    Integer exists = userOrgRefMapper.existRefByOrgIds(orgIds);
    if (exists > 0) {
      throw new ApiException(SysErrorCodeConstants.ORG_HAS_USER);
    }
    //检查上级部门的是否为叶子结点
    SysOrg sysOrg = orgMapper.selectById(orgId);
    if (StringUtils.isNotBlank(sysOrg.getParentId())){
      List<SysOrg> sonOrgs = orgMapper.selectList(new QueryWrapperX<SysOrg>()
              .eq("parent_id", sysOrg.getParentId()));
      List<SysOrg> collect = sonOrgs.stream().filter(org -> !org.getSysOrgId().equals(orgId))
              .collect(Collectors.toList());
      //如果为空则不存在下级部门，更新isLeaf状态
      if (CollectionUtils.isEmpty(collect)){
        SysOrg parentOrg = orgMapper.selectById(sysOrg.getParentId());
        parentOrg.setIsLeaf(true);
        orgMapper.updateById(parentOrg);
      }
    }
    // 移除本部门及其子部门
    super.removeByIds(orgIds);
  }

  /**
   * 批量删除部门
   * @param ids
   */
  @Override
  public void deleteOrgBatch(List<String> ids) {
    // 删除部门前需检测当前部门/下级部门是否存在用户，如果存在，不允许删除并返回提示
    List<String> orgIds = findChildren(ids);
    // 部门/子部门下是否存在用户
    Integer exists = userOrgRefMapper.existRefByOrgIds(orgIds);
    if (exists > 0) {
      throw new ApiException(SysErrorCodeConstants.ORG_HAS_USER);
    }
    // 移除本部门及其子部门
    super.removeByIds(orgIds);
  }

  /**
   * 获取所有下级子部门id(包含当前部门)
   *
   * @param orgIds 上级部门id
   * @return 子部门id集合
   */
  public List<String> findChildren(List<String> orgIds) {
    QueryWrapperX<SysOrg> queryWrapperX = new QueryWrapperX<>();
    queryWrapperX.inIfPresent("parent_id", orgIds);
    List<SysOrg> sysOrgs = orgMapper.selectList(queryWrapperX);
    List<String> childrenIds = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(sysOrgs)) {
      childrenIds =
          sysOrgs.stream()
              .filter(org -> StringUtils.isNotBlank(org.getParentId()))
              .map(SysOrg::getSysOrgId)
              .collect(Collectors.toList());
      childrenIds.addAll(findChildren(childrenIds));
    }
    // 需包含上级部门id
    childrenIds.addAll(orgIds);
    return childrenIds;
  }

  /**
   * 组织架构树形查询 采用逐步加载方式，避免数据量过载
   *
   * @param id 组织架构id
   * @return 组织机构树形机构
   */
  @Override
  public List<SysOrgNodeVO> treeList(String id) {
    // 根据id加载该部门下所有子部门，如果id为空，则默认查询所有一级部门(parent_id为空)
    if (StringUtils.isBlank(id)) {
      QueryWrapperX<SysOrg> orgQueryWrapperX = new QueryWrapperX<>();
      orgQueryWrapperX.isNull("parent_id");
      List<SysOrg> orgList = orgMapper.selectList(orgQueryWrapperX);
      return SysOrgConvert.INSTANCE.convertNode(orgList);
    }
    List<SysOrg> orgList = orgMapper.selectList("parent_id", id);
    return orgList.stream()
        .filter(org -> StringUtils.isBlank(org.getParentId()))
        .map(org -> covertOrgNode(org, orgList))
        .collect(Collectors.toList());
  }

  /**
   * 将SysOrg转化为SysOrgNodeVO并设置children属性
   *
   * @param org 某组织机构信息
   * @param orgList 组织机构集合
   * @return 组织机构节点信息
   */
  private SysOrgNodeVO covertOrgNode(SysOrg org, List<SysOrg> orgList) {
    SysOrgNodeVO node = SysOrgConvert.INSTANCE.convertNode(org);
    List<SysOrgNodeVO> children =
        orgList.stream()
            .filter(subOrg -> subOrg.getParentId().equals(subOrg.getSysOrgId()))
            .map(subOrg -> covertOrgNode(subOrg, orgList))
            .collect(Collectors.toList());
    node.setChildren(children);
    return node;
  }

  /**
   * 获取组织机构树形结构(异步)
   *
   * @param reqVO 树形机构查询条件
   * @return 组织机构树形结构
   */
  @Override
  public List<SysOrgNodeVO> queryTreeAsync(SysOrgTreeReqVO reqVO) {
    // 根据id加载该部门下所有子部门，如果id为空，则默认查询所有一级部门(parent_id为空)
    if (StringUtils.isBlank(reqVO.getParentId())
        && StringUtils.isBlank(reqVO.getName())
        && StringUtils.isBlank(reqVO.getOrgCode())) {
      QueryWrapperX<SysOrg> orgQueryWrapperX = new QueryWrapperX<>();
      orgQueryWrapperX
          .or(wrapper -> wrapper.isNull("parent_id").or().eq("parent_id", ""))
          .and(wrapper -> wrapper.eq("type", OrgTypeEnum.INTERNAL.getValue()));
      List<SysOrg> orgList = orgMapper.selectList(orgQueryWrapperX);
      return SysOrgConvert.INSTANCE.convertNode(orgList);
    }

    // 获取下级部门节点
    QueryWrapperX<SysOrg> queryWrapperX = new QueryWrapperX<>();
    queryWrapperX
        .eq(StringUtils.isNotBlank(reqVO.getParentId()), "parent_id", reqVO.getParentId())
        .eq("type", OrgTypeEnum.INTERNAL.getValue())
        .likeIfPresent("org_name", reqVO.getName())
        .likeIfPresent("org_code", reqVO.getOrgCode())
        .orderByAsc("order_by");
    List<SysOrg> orgList = orgMapper.selectList(queryWrapperX);
    return SysOrgConvert.INSTANCE.convertNode(orgList);
  }

  private SysOrgNodeVO generateTreeFromChildren(SysOrg org) {
    if (ObjectUtils.isNotEmpty(org)) {
      QueryWrapperX<SysOrg> queryWrapperX = new QueryWrapperX<>();
      queryWrapperX.eq("sys_org_id", org.getParentId());
      SysOrg parentOrg = orgMapper.selectOne("sys_org_id", org.getParentId());
      Optional.ofNullable(parentOrg)
          .ifPresent(
              parent -> {
                if (StringUtils.isNotBlank(parent.getParentId())) {
                  generateTreeFromChildren(parent);
                }
              });
      SysOrgNodeVO parentNode = SysOrgConvert.INSTANCE.convertNode(parentOrg);
      logger.info("####### parentNode:{}", parentNode);
      if (Objects.nonNull(parentNode)) {
        parentNode.setChildren(Collections.singletonList(SysOrgConvert.INSTANCE.convertNode(org)));
      }
      return parentNode;
    }
    return null;
  }

  /** 根据关键字搜索相关的部门数据 */
  @Override
  public List<SysDepartTreeModel> searchBy(SysOrgTreeReqVO reqVO) {
    // todo 下级部门搜索条件待完成
    return null;
  }

  /**
   * 筛选部门列表
   *
   * @param reqVO 筛选条件请求 VO
   * @return 部门列表
   */
  @Override
  public IPage<SysOrgRespVO> page(SysOrgPageReqVO reqVO) {
    // 获取上级部门状态
    SysOrg parentOrg = orgMapper.selectOne("sys_org_id", reqVO.getParentId());
    if (StringUtils.isNotBlank(reqVO.getCode())) {
      reqVO.setCode(Util.escapeStr(reqVO.getCode()));
    }
    if (StringUtils.isNotBlank(reqVO.getName())) {
      reqVO.setName(Util.escapeStr(reqVO.getName()));
    }

    Page<SysOrgRespVO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
    IPage<SysOrgRespVO> pageResult = orgMapper.selectOrgList(page, reqVO);
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
    return pageResult;
  }

  @Override
  public IPage<SysOrgRespVO> selAllOrg(SysOrgPageReqVO reqVO) {
    Page<SysOrgRespVO> page = new Page<>(reqVO.getPageNo(), reqVO.getPageSize());
    IPage<SysOrgRespVO> pageResult = orgMapper.SelAllOrg(page, reqVO);
    return pageResult;
  }

  /**
   * 获得部门信息
   *
   * @param id 部门编号
   * @return 部门信息
   */
  @Override
  public SysOrgInfoRespVO getDept(String id) {
    SysOrg sysOrg = orgMapper.selectById(id);
    AtomicReference<SysOrgInfoRespVO> vo = new AtomicReference<>(new SysOrgInfoRespVO());
    Optional.ofNullable(sysOrg).ifPresent(org -> vo.set(SysOrgConvert.INSTANCE.convertInfoVO(org)));
    // 获取上级部门名称
    if (StringUtils.isNotBlank(sysOrg.getParentId())) {
      QueryWrapperX<SysOrg> queryWrapperX = new QueryWrapperX<>();
      queryWrapperX.select("org_name").eq("sys_org_id", sysOrg.getParentId());
      SysOrg parentOrg = orgMapper.selectOne(queryWrapperX);
      vo.get().setParentName(parentOrg.getOrgName());
    }

    return vo.get();
  }

  /**
   * 从缓存中获取部门机构信息
   *
   * @param parentId 部门编号
   * @param recursive 是否递归获取所有
   * @return 子部门列表
   */
  @Override
  public List<SysOrg> getOrgFromCache(String parentId, boolean recursive) {
    return null;
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
    Optional.ofNullable(sysOrgs)
        .ifPresent(
            orgs -> {
              Set<String> parentIds =
                  orgs.stream().map(SysOrg::getParentId).collect(Collectors.toSet());
              QueryWrapperX<SysOrg> parentWrapper = new QueryWrapperX<>();
              parentWrapper.inIfPresent("sys_org_id", parentIds);
              List<SysOrg> parentOrgs = orgMapper.selectList(parentWrapper);
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
    List<String> childrenIds = findChildren(reqVO.getIds());
    // 获取所有下属部门人员id
    QueryWrapperX<SysUserOrgRef> queryWrapperX = new QueryWrapperX<>();
    queryWrapperX.inIfPresent("sys_org_id", childrenIds);
    List<SysUserOrgRef> sysUserOrgRefs = userOrgRefMapper.selectList(queryWrapperX);
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
    if (CollectionUtils.isNotEmpty(childrenIds)) {
      orgMapper.updateStatusBatch(childrenIds, reqVO.getStatus());
    }
    return true;
  }

  /**
   * 获取组织机构精简信息(前端新增部门时联想值)
   *
   * @param reqVO
   * @return
   */
  @Override
  public List<SysOrgSimpleRespVO> getSimpleOrg(SysOrgListReqVO reqVO) {
    return orgRepository.getSimpleOrg(reqVO);
  }

  /**
   * 获取对应组织机构用户
   *
   * @param param 组织机构用户请求参数
   * @return 组织机构用户列表
   */
  @Override
  public List<InternalUserSimpleVO> getUserByDeptId(OrgUserParam param) {
    return orgMapper.getUserByOrgId(param);
  }

  public void checkCreateOrUpdate(String id, String parentId, String name) {
    // 校验自己存在
    checkDeptExists(id);
    // 校验父部门的有效性
    checkParentDeptEnable(id, parentId);
    // 校验部门名的唯一性
    checkDeptNameUnique(id, parentId, name);
  }

  private void checkParentDeptEnable(String id, String parentId) {
    if (StringUtils.isBlank(parentId)) {
      return;
    }
    // 不能设置自己为父部门
    if (parentId.equals(id)) {
      throw new ApiException(SysErrorCodeConstants.ORG_PARENT_ERROR);
    }
    // 父岗位不存在
    SysOrg org = orgMapper.selectById(parentId);
    if (org == null) {
      throw new ApiException(SysErrorCodeConstants.ORG_PARENT_NOT_EXITS);
    }
    // 父部门被禁用
    if (SysCommonStatusEnum.DISABLE.getStatus().booleanValue() == org.getStatus().booleanValue()) {
      throw new ApiException(SysErrorCodeConstants.ORG_NOT_ENABLE);
    }
  }

  private void checkDeptExists(String id) {
    if (id == null) {
      return;
    }
    SysOrg dept = orgMapper.selectById(id);
    if (dept == null) {
      throw new ApiException(SysErrorCodeConstants.ORG_NOT_FOUND);
    }
  }

  private void checkDeptNameUnique(String id, String parentId, String name) {
    SysOrg org = orgMapper.selectByParentIdAndName(parentId, name);
    if (org == null) {
      return;
    }
    // 如果 id 为空，说明不用比较是否为相同 id 的岗位
    if (id == null) {
      throw new ApiException(SysErrorCodeConstants.ORG_NAME_DUPLICATE);
    }
    if (!org.getSysOrgId().equals(id)) {
      throw new ApiException(SysErrorCodeConstants.ORG_NAME_DUPLICATE);
    }
  }

  /**校验统一社会信用代码唯一*/
  public void checkCreditCodeUnique(String creditCode,String id){
    if (id !=null){
      //编辑校验
      List<SysOrg> list = orgMapper.selectList(new QueryWrapperX<SysOrg>().eq("org_credit_code", creditCode));
      SysOrg org = list.stream().filter(s -> !s.getSysOrgId().equals(id)).findAny().orElse(null);
      if (null != org){
        throw new ApiException(SysErrorCodeConstants.ORG_CREDIT_EXITS);
      }
    }else {
      //创建校验
      Integer count = orgMapper.selectCount(new QueryWrapperX<SysOrg>().eq(StringUtils.isNotBlank(creditCode),"org_credit_code",creditCode));
      if (count>0){
        throw new ApiException(SysErrorCodeConstants.ORG_CREDIT_EXITS);
      }
    }
  }
  /**校验手机号唯一*/
  public  void checkPhoneUnique(String phone,String id){
    if (id !=null){
      List<SysOrg> list = orgMapper.selectList(new QueryWrapperX<SysOrg>().eq("phone", phone));
      SysOrg org = list.stream().filter(s -> !s.getSysOrgId().equals(id)).findAny().orElse(null);
      if (null != org){
        throw new ApiException(SysErrorCodeConstants.ORG_MOBILE_EXISTS);
      }
    }else {
        Integer count = orgMapper.selectCount(new QueryWrapperX<SysOrg>().eq(StringUtils.isNotBlank(phone),"phone",phone));      if (count>0){
        if (count>0){
          throw new ApiException(SysErrorCodeConstants.ORG_MOBILE_EXISTS);
        }
      }
    }
  }

  /**校验组织机构名称唯一*/
  public void checkTitleUnique(String title,String id){
    if (id !=null){
      List<SysOrg> list = orgMapper.selectList(new QueryWrapperX<SysOrg>().eq("org_name", title));
      SysOrg org = list.stream().filter(s -> !s.getSysOrgId().equals(id)).findAny().orElse(null);
      if (null != org){
        throw new ApiException(SysErrorCodeConstants.ORG_NAME_EXITS);
      }
    }else {
      Integer count = orgMapper.selectCount(new QueryWrapperX<SysOrg>().eq(StringUtils.isNotBlank(title),"org_name",title));
      if (count>0){
        throw new ApiException(SysErrorCodeConstants.ORG_NAME_EXITS);
      }
    }
  }
}
