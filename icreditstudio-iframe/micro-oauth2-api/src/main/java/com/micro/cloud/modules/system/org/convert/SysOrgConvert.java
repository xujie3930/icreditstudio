package com.micro.cloud.modules.system.org.convert;

import com.micro.cloud.modules.system.org.dataobject.SysOrg;
import com.micro.cloud.modules.system.org.vo.SysDepartTreeModel;
import com.micro.cloud.modules.system.org.vo.SysOrgBaseVO;
import com.micro.cloud.modules.system.org.vo.SysOrgCreateReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgInfoRespVO;
import com.micro.cloud.modules.system.org.vo.SysOrgNodeVO;
import com.micro.cloud.modules.system.org.vo.SysOrgRespVO;
import com.micro.cloud.modules.system.org.vo.SysOrgUpdateReqVO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 〈组织机构数据转换〉
 *
 * @author roy
 * @create 2021/11/10
 * @since 1.0.0
 */
@Mapper
public interface SysOrgConvert {

  SysOrgConvert INSTANCE = Mappers.getMapper(SysOrgConvert.class);

  /**
   * 组织机构基本信息转换
   *
   * @param bean 组织机构基本信息
   * @return 组织机构数据库实体类
   */
  SysOrg convertDo(SysOrgBaseVO bean);

  /**
   * 组织机构节点信息转换
   *
   * @param bean 组织机构节点信息
   * @return 组织机构数据库实体类
   */
  @Mappings({
    @Mapping(source = "sysOrgId", target = "key"),
    @Mapping(source = "orgName", target = "title")
  })
  SysOrgNodeVO convertNode(SysOrg bean);

  /**
   * 组织机构节点信息转换(List)
   *
   * @param bean 组织机构节点信息
   * @return 组织机构数据库实体类
   */
  @Mappings({
    @Mapping(source = "sysOrgId", target = "key"),
    @Mapping(source = "orgName", target = "title")
  })
  List<SysOrgNodeVO> convertNode(List<SysOrg> bean);

  /**
   * 创建组织机构信息 -> 数据库实体类
   *
   * @param bean 创建组织机构信息
   * @return 数据库实体类
   */
  @Mappings({
    @Mapping(source = "title", target = "orgName"),
    @Mapping(source = "creditCode", target = "orgCreditCode")
  })
  SysOrg convertDO(SysOrgCreateReqVO bean);

  /**
   * 更新组织机构信息 -> 数据库实体类
   *
   * @param bean 更新组织机构信息
   * @return 数据库实体类
   */
  @Mappings({
    @Mapping(source = "id", target = "sysOrgId"),
    @Mapping(source = "title", target = "orgName"),
    @Mapping(source = "creditCode", target = "orgCreditCode")
  })
  SysOrg convertDO(SysOrgUpdateReqVO bean);

  /**
   * 数据库实体类转换为视图展示信息
   *
   * @param org 数据库实体类
   * @return 视图展示信息
   */
  @Mappings({@Mapping(source = "sysOrgId", target = "id")})
  SysOrgRespVO convertVO(SysOrg org);

  /**
   * 数据库实体类转换为视图展示信息
   *
   * @param org 数据库实体类
   * @return 视图展示信息
   */
  @Mappings({
    @Mapping(source = "sysOrgId", target = "id"),
    @Mapping(source = "orgName", target = "title"),
    @Mapping(source = "orgCode", target = "code")
  })
  List<SysOrgRespVO> convertVO(List<SysOrg> org);

  /**
   * 数据库实体类转换为视图展示信息
   *
   * @param org 数据库实体类
   * @return 视图展示信息
   */
  @Mappings({
    @Mapping(source = "sysOrgId", target = "id"),
    @Mapping(source = "orgName", target = "title"),
    @Mapping(source = "type", target = "orgType"),
    @Mapping(source = "orgCreditCode", target = "creditCode")
  })
  SysOrgInfoRespVO convertInfoVO(SysOrg org);
}
