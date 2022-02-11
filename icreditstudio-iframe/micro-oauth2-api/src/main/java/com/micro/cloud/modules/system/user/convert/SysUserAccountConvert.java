package com.micro.cloud.modules.system.user.convert;

import com.micro.cloud.modules.system.user.dataobject.SysUserAccount;
import com.micro.cloud.modules.system.user.vo.SysUserAccountCreateReqVO;
import com.micro.cloud.modules.system.user.vo.external.ExternalUserCreateReqVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserCreateReqVO;
import com.micro.cloud.modules.system.user.vo.org.OrgUserCreateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 〈用户账号信息转换接口〉
 *
 * @author roy
 * @create 2021/11/6
 * @since 1.0.0
 */
@Mapper
public interface SysUserAccountConvert {

  SysUserAccountConvert INSTANCE =  Mappers.getMapper(SysUserAccountConvert.class);

  /**
   * 用户注册/创建信息转为数据库实体类
   * @param vo 用户注册/创建信息
   * @return 数据库实体类
   */
  SysUserAccount convertDO(SysUserAccountCreateReqVO vo);

  /**
   * 个人用户创建信息转换
   * @param reqVO 个人用户信息
   * @return 用户账号信息
   */
  SysUserAccountCreateReqVO convertVO(ExternalUserCreateReqVO reqVO);

  /**
   * 内部用户创建信息转换
   * @param reqVO 内部用户信息
   * @return 用户账号信息
   */
  SysUserAccountCreateReqVO convertVO(InternalUserCreateReqVO reqVO);


  /**
   * 机构用户创建信息转换
   * @param reqVO 内部用户信息
   * @return 用户账号信息
   */
  SysUserAccountCreateReqVO convertVO(OrgUserCreateReqVO reqVO);

}
