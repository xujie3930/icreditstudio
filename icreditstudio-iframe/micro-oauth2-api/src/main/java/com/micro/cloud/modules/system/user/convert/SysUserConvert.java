package com.micro.cloud.modules.system.user.convert;

import com.micro.cloud.api.CommonPage;
import com.micro.cloud.domian.dto.UserDTO;
import com.micro.cloud.modules.system.org.vo.ExternalOrgCreateVO;
import com.micro.cloud.modules.system.org.vo.SysOrgBaseVO;
import com.micro.cloud.modules.system.user.vo.CommonUserInfoVo;
import com.micro.cloud.modules.system.user.vo.CommonUserInfoRepVO;
import com.micro.cloud.modules.system.user.vo.external.ExternalUserCreateReqVO;
import com.micro.cloud.modules.system.user.vo.external.ExternalUserRegisterReqVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserCreateReqVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserInfoVO;
import com.micro.cloud.modules.system.user.vo.org.OrgUserCreateReqVO;
import com.micro.cloud.modules.system.user.vo.org.OrgUserInfoVO;
import com.micro.cloud.modules.system.user.vo.SysUserUpdateReqVO;
import com.micro.cloud.modules.system.user.vo.external.ExternalUserInfoVO;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.modules.system.user.vo.org.OrgUserRegisterReqVO;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author EDZ
 */
@Mapper
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    /**
     * 更新用户请求体 -> 用户实体类
     *
     * @param bean 更新用户请求体
     * @return 用户实体类
     */
    @Mappings({
            @Mapping(source = "userId", target = "sysUserId"),
            @Mapping(source = "username", target = "userName"),
            @Mapping(source = "mobile", target = "phone")
    })
    SysUser convertDO(OrgUserInfoVO bean);

    /**
     * 更新用户请求体 -> 用户实体类
     *
     * @param bean 更新用户请求体
     * @return 用户实体类
     */
    @Mappings({
            @Mapping(source = "userId", target = "sysUserId"),
            @Mapping(source = "username", target = "userName"),
            @Mapping(source = "mobile", target = "phone")
    })
    SysUser convertDO(SysUserUpdateReqVO bean);

    /**
     * 新增个人用户请求体 -> 用户实体类
     *
     * @param bean 新增用户请求体
     * @return 用户实体类
     */
    @Mappings({
            @Mapping(source = "username", target = "userName"),
            @Mapping(source = "mobile", target = "phone")
    })
    SysUser convertDO(ExternalUserCreateReqVO bean);

    /**
     * 机构用户创建信息 -> 用户数据库实体类
     *
     * @param bean
     * @return
     */
    SysUser convertDO(OrgUserCreateReqVO bean);

    /**
     * 机构用户注册信息转换
     *
     * @param bean
     * @return
     */
    @Mappings({@Mapping(source = "mobile", target = "phone")})
    SysUser convertDO(OrgUserRegisterReqVO bean);

    /**
     * 用户实体类 -> 个人用户信息
     *
     * @param bean 用户实体类
     * @return 个人用户信息
     */
    @Mappings({
            @Mapping(source = "sysUserId", target = "userId"),
            @Mapping(source = "userName", target = "username"),
            @Mapping(source = "phone", target = "mobile"),
            @Mapping(source = "sysUserId", target = "key")
    })
    ExternalUserInfoVO convertVO(SysUser bean);

    /**
     * 用户实体类集合 -> 个人用户信息集合
     *
     * @param list 用户实体类集合
     * @return 个人用户信息集合
     */
    @Mappings({
            @Mapping(source = "sysUserId", target = "userId"),
            @Mapping(source = "sysUserId", target = "key")
    })
    List<ExternalUserInfoVO> convertVO(List<SysUser> list);

    /**
     * 用户实体类 -> 机构用户信息
     *
     * @param bean 用户实体类
     * @return 机构用户信息
     */
    @Mappings({
            @Mapping(source = "sysUserId", target = "userId"),
            @Mapping(source = "userName", target = "username"),
            @Mapping(source = "phone", target = "mobile"),
            @Mapping(source = "sysUserId", target = "key")
    })
    OrgUserInfoVO convertOrgVO(SysUser bean);

    /**
     * 用户实体类 -> 机构用户信息
     *
     * @param bean 用户实体类
     * @return 机构用户信息
     */
    @Mappings({
            @Mapping(source = "sysUserId", target = "userId"),
            @Mapping(source = "userName", target = "username"),
            @Mapping(source = "phone", target = "mobile"),
    })
    List<OrgUserInfoVO> convertOrgVO(List<SysUser> bean);

    /**
     * 内部用户创建信息 -> 用户数据库实体类
     *
     * @param bean
     * @return
     */
    @Mappings({
            @Mapping(source = "username", target = "userName"),
            @Mapping(source = "mobile", target = "phone")
    })
    SysUser convertDO(InternalUserCreateReqVO bean);

    /**
     * 用户实体类 -> 系统用户信息
     *
     * @param bean 用户实体类
     * @return 机构用户信息
     */
    @Mappings({
            @Mapping(source = "sysUserId", target = "userId"),
            @Mapping(source = "userName", target = "username"),
            @Mapping(source = "phone", target = "mobile"),
            @Mapping(source = "sysUserId", target = "key")
    })
    InternalUserInfoVO convertInternalVO(SysUser bean);

    /**
     * 用户实体类 -> 系统用户信息
     *
     * @param bean 用户实体类
     * @return 机构用户信息
     */
    @Mappings({
            @Mapping(source = "sysUserId", target = "userId"),
            @Mapping(source = "userName", target = "username"),
            @Mapping(source = "realName", target = "realName"),
            @Mapping(source = "phone", target = "mobile")
    })
    List<InternalUserInfoVO> convertInternalVO(List<SysUser> bean);

    /**
     * 个人用户注册信息转为创建用户信息
     *
     * @param bean 注册信息
     * @return 创建用户信息
     */
    ExternalUserCreateReqVO convertCreatReqVO(ExternalUserRegisterReqVO bean);

    /**
     * 机构用户注册信息转为创建用户信息
     *
     * @param bean 注册信息
     * @return 创建用户信息
     */
    OrgUserCreateReqVO convertCreatReqVO(OrgUserRegisterReqVO bean);

    /**
     * 机构用户注册时组织架构数据转换
     *
     * @param bean
     * @return
     */
    @Mappings({@Mapping(source = "type", target = "orgType")})
    SysOrgBaseVO convertCreatReqVO(ExternalOrgCreateVO bean);

    /**
     * 数据库实体类转换为dto
     *
     * @param bean 数据库实体类
     * @return
     */
    @Mappings({
            @Mapping(source = "sysUserId", target = "id"),
            @Mapping(source = "userName", target = "username")
    })
    UserDTO convertDto(SysUser bean);

    /**
     * 用户信息转换
     *
     * @param commonInfo 用户信息
     * @return
     */
    CommonUserInfoVo convertInfoRepVO(CommonUserInfoRepVO commonInfo);

    /**
     * 个人通用信息转换
     *
     * @param bean
     * @return
     */
    @Mappings({
            @Mapping(source = "sysUserId", target = "userId"),
            @Mapping(source = "userName", target = "username")
    })
    CommonUserInfoVo convertCommonInfoVO(SysUser bean);

    /**
     * 机构用户更新信息转换
     *
     * @param bean
     * @return
     */
    SysUserUpdateReqVO convertUpdateVo(OrgUserInfoVO bean);

    /**
     * 用户信息转换
     *
     * @param bean
     * @return
     */
    @Mappings({@Mapping(source = "userId", target = "sysUserId")})
    SysUser convertDO(CommonUserInfoVo bean);

    /**
     * 用户更改信息转换
     *
     * @param bean
     * @return
     */
    SysUserUpdateReqVO convertUpdateVo(CommonUserInfoVo bean);

    /**
     * 分页数据转换
     *
     * @param page 系统用户分页数据
     * @return 前端视图展示数据
     */
    CommonPage<InternalUserInfoVO> covertPage(CommonPage<SysUser> page);


}
