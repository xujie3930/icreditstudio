package com.micro.cloud.modules.system.org.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageHelper;
import com.micro.cloud.api.CommonPage;
import com.micro.cloud.modules.system.org.dataobject.SysOrg;
import com.micro.cloud.modules.system.org.param.OrgUserParam;
import com.micro.cloud.modules.system.org.vo.SysOrgBaseVO;
import com.micro.cloud.modules.system.org.vo.SysOrgListReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgPageReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgRespVO;
import com.micro.cloud.modules.system.org.vo.SysOrgSimpleRespVO;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserSimpleVO;
import com.micro.cloud.modules.system.user.vo.org.OrgUserPageRepVO;
import com.micro.cloud.mybatis.core.mapper.BaseMapperX;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper 接口
 *
 * @author EDZ
 * @since 2021-11-05
 */
@Mapper
public interface SysOrgMapper extends BaseMapperX<SysOrg> {

  /**
   * 根据用户id集合获取对应组织信息
   *
   * @param ids 用户id集合
   * @return 用户对应组织信息
   */
  @MapKey(value = "userId")
  Map<String, OrgUserPageRepVO> getOrgByUserIds(@Param("ids") List<String> ids);

  /**
   * 根据组织机构名称/状态查询
   *
   * @param reqVO
   * @return
   */
  default CommonPage<SysOrg> selectPage(SysOrgPageReqVO reqVO) {
    PageHelper.startPage(reqVO.getPageNo(), reqVO.getPageSize());
    return selectPage(
        reqVO,
        new QueryWrapperX<SysOrg>()
            .eqIfPresent("parent_id", reqVO.getParentId())
            .likeIfPresent("org_name", reqVO.getName())
            .eqIfPresent("status", reqVO.getStatus()));
  }

  /**
   * 根据条件获取组织机构/部门列表
   *
   * @param reqVO
   * @return
   */
  // List<SysOrgRespVO> selectOrgList(@Param("vo") SysOrgPageReqVO reqVO);

  IPage<SysOrgRespVO> selectOrgList(IPage<SysOrgRespVO> page, @Param("vo") SysOrgPageReqVO reqVO);

  /**
   * 查询所有部门
   * @param page
   * @param reqVO
   * @return
   */
  IPage<SysOrgRespVO> SelAllOrg(IPage<SysOrgRespVO> page, @Param("vo") SysOrgPageReqVO reqVO);

  /**
   * 根据组织机构名称/状态查询
   *
   * @param reqVO
   * @return
   */
  default List<SysOrg> selectList(SysOrgListReqVO reqVO) {
    return selectList(
        new QueryWrapperX<SysOrg>()
            .likeIfPresent("org_name", reqVO.getName())
            .eqIfPresent("status", reqVO.getStatus()));
  }

  /**
   * 根据上级部门ID和部门名称查询
   *
   * @param parentId
   * @param name
   * @return
   */
  default SysOrg selectByParentIdAndName(String parentId, String name) {
    return selectOne(new QueryWrapper<SysOrg>().eq("parent_id", parentId).eq("org_name", name));
  }

  /**
   * 获取当前部门下级部门数量
   *
   * @param parentId
   * @return
   */
  default Integer selectCountByParentId(String parentId) {
    return selectCount(new QueryWrapper<SysOrg>().eq("parent_id", parentId));
  }

  /**
   * 批量更新部门状态
   *
   * @param childrenIds 下级部门id
   * @param status 状态 参见 SysCommonStatusEnum 枚举类
   */
  void updateStatusBatch(
      @Param(value = "orgIds") List<String> childrenIds, @Param(value = "status") Boolean status);

  /**
   * 获取组织机构精简信息
   *
   * @param reqVO 组织机构查询请求参数
   * @return 组织机构列表
   */
  List<SysOrgSimpleRespVO> getSimpleOrg(@Param(value = "vo") SysOrgListReqVO reqVO);

  /**
   * 获取组织机构用户
   *
   * @param param 查询参数
   * @return 用户列表
   */
  List<InternalUserSimpleVO> getUserByOrgId(@Param(value = "param") OrgUserParam param);

  /**
   * 根据组织名称获取组织名称
   *
   * @param name 查询参数
   * @return 组织
   */

  SysOrg getSysOrgByName(String name);

  /**
   * 根据创建者名称获取组织名称
   *
   * @param name 查询参数
   * @return 组织
   */

  List<SysOrg> getSysOrgByCreatorName(String name);


}
