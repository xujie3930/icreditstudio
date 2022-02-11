package com.micro.cloud.modules.system.user.repository.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.micro.cloud.api.CommonPage;
import com.micro.cloud.enums.UserTypeEnum;
import com.micro.cloud.modules.system.user.convert.SysUserConvert;
import com.micro.cloud.modules.system.user.dataobject.SysUser;
import com.micro.cloud.modules.system.user.mapper.SysUserMapper;
import com.micro.cloud.modules.system.user.repository.ISysUserRepository;
import com.micro.cloud.modules.system.user.vo.SysUserPageReqVO;
import com.micro.cloud.modules.system.user.vo.external.ExternalUserInfoVO;
import com.micro.cloud.modules.system.user.vo.internal.InternalUserInfoVO;
import com.micro.cloud.modules.system.user.vo.org.OrgUserInfoVO;
import com.micro.cloud.mybatis.core.query.QueryWrapperX;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * 〈〉
 *
 * @author roy
 * @create 2021/11/6
 * @since 1.0.0
 */
@Repository
public class SysUserRepositoryImpl implements ISysUserRepository {

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 个人用户信息分页查询
     *
     * @param vo     分页请求参数
     * @param userId 当前用户id
     * @return 分页记录
     */
    @Override
    public List<ExternalUserInfoVO> externalUserPage(SysUserPageReqVO vo, String userId) {
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        QueryWrapperX<SysUser> queryWrapper = new QueryWrapperX<>();
        queryWrapper
                .eq(Objects.nonNull(vo.getStatus()), "status", vo.getStatus())
                .eq("type", UserTypeEnum.EXTERNAL.getValue())
                .betweenIfPresent("create_time", vo.getBeginTime(), vo.getEndTime())
                .like(StringUtils.isNotBlank(vo.getUsername()), "user_name", vo.getUsername())
                .orderByDesc(StringUtils.isNotBlank(vo.getOrderBy()), vo.getOrderBy())
                .orderByDesc("create_time");
        List<SysUser> result = this.userMapper.selectList(queryWrapper);
        return SysUserConvert.INSTANCE.convertVO(result);
    }

    /**
     * 机构用户分页查询
     *
     * @param vo     分页请求参数
     * @param userId 当前用户id
     * @return 分页记录
     */
    @Override
    public List<OrgUserInfoVO> orgUserPage(SysUserPageReqVO vo, String userId) {
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        QueryWrapperX<SysUser> queryWrapper = new QueryWrapperX<>();
        queryWrapper
                .eq(Objects.nonNull(vo.getStatus()), "status", vo.getStatus())
                .eq("type", UserTypeEnum.ORGANIZATION.getValue())
                .betweenIfPresent("create_time", vo.getBeginTime(), vo.getEndTime())
                .like(StringUtils.isNotBlank(vo.getUsername()), "user_name", vo.getUsername())
                .orderByDesc(StringUtils.isNotBlank(vo.getOrderBy()), vo.getOrderBy())
                .orderByDesc("create_time");
        List<SysUser> orgUsers = this.userMapper.selectList(queryWrapper);
        return SysUserConvert.INSTANCE.convertOrgVO(orgUsers);
    }

    /**
     * 系统用户分页查询
     *
     * @param vo     分页请求参数
     * @param userId 当前用户id
     * @return 分页记录
     */
    @Override
    public CommonPage<SysUser> internalUserPage(SysUserPageReqVO vo, String userId) {
//    PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        QueryWrapperX<SysUser> queryWrapper = new QueryWrapperX<>();
        boolean sort = vo.isSort();
        if (sort == true) {
            queryWrapper
                    .eq(Objects.nonNull(vo.getStatus()), "status", vo.getStatus())
                    .eq("type", UserTypeEnum.INTERNAL.getValue())
                    .betweenIfPresent("create_time", vo.getBeginTime(), vo.getEndTime())
                    .like(StringUtils.isNotBlank(vo.getUsername()), "user_name", vo.getUsername())
                    .like(StringUtils.isNotBlank(vo.getRealName()), "real_name", vo.getRealName())
                    .orderByAsc(StringUtils.isNotBlank(vo.getOrderBy()), vo.getOrderBy());
        } else {
            queryWrapper
                    .eq(Objects.nonNull(vo.getStatus()), "status", vo.getStatus())
                    .eq("type", UserTypeEnum.INTERNAL.getValue())
                    .betweenIfPresent("create_time", vo.getBeginTime(), vo.getEndTime())
                    .like(StringUtils.isNotBlank(vo.getUsername()), "user_name", vo.getUsername())
                    .like(StringUtils.isNotBlank(vo.getRealName()), "real_name", vo.getRealName())
                    .orderByDesc(StringUtils.isNotBlank(vo.getOrderBy()), vo.getOrderBy());
        }
        return userMapper.selectPage(vo, queryWrapper);
    }
}
