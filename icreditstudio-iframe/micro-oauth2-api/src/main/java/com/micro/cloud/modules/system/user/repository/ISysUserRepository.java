package com.micro.cloud.modules.system.user.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.micro.cloud.api.CommonPage;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserInfoVO;
import com.micro.cloud.modules.system.user.vo.org.OrgUserInfoVO;
import com.micro.cloud.modules.system.user.vo.SysUserPageReqVO;
import com.micro.cloud.modules.system.user.vo.external.ExternalUserInfoVO;

import java.util.List;

/**
 * 〈用户表操作封装接口〉
 *
 * @author roy
 * @create 2021/11/6
 * @since 1.0.0
 */
public interface ISysUserRepository {

    /**
     * 个人用户分页查询
     *
     * @param vo     分页请求参数
     * @param userId 当前用户id
     * @return 分页记录
     */
    List<ExternalUserInfoVO> externalUserPage(SysUserPageReqVO vo, String userId);

    /**
     * 机构用户分页查询
     *
     * @param vo     分页请求参数
     * @param userId 当前用户id
     * @return 分页记录
     */
    List<OrgUserInfoVO> orgUserPage(SysUserPageReqVO vo, String userId);

    /**
     * 机构用户分页查询
     *
     * @param vo     分页请求参数
     * @param userId 当前用户id
     * @return 分页记录
     */
    CommonPage<SysUser> internalUserPage(SysUserPageReqVO vo, String userId);
}
