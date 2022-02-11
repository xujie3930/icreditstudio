package com.micro.cloud.modules.system.common.mapper;

import com.micro.cloud.modules.system.common.dataobject.SysSmsCode;
import com.micro.cloud.mybatis.core.mapper.BaseMapperX;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import org.apache.ibatis.annotations.Mapper;

/** @author roy */
@Mapper
public interface SysSmsCodeMapper extends BaseMapperX<SysSmsCode> {

  /**
   * 获得手机号的最后一个手机验证码
   *
   * @param mobile 手机号
   * @param scene 发送场景，选填
   * @return 手机验证码
   */
  default SysSmsCode selectLastByMobile(String mobile, Integer scene) {
    return selectOne(
        new QueryWrapperX<SysSmsCode>()
            .eqIfPresent("mobile", mobile)
            .eqIfPresent("scene", scene)
            .orderByDesc("id")
            .last("LIMIT 1"));
  }
}
