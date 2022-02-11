package com.micro.cloud.modules.system.supplier.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.micro.cloud.modules.system.org.vo.SysOrgPageReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgRespVO;
import com.micro.cloud.modules.system.supplier.entity.SysSupplierRef;
import com.micro.cloud.modules.system.supplier.vo.SysSupPageReqVo;
import com.micro.cloud.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 供应商层级关系
 * @author: zlj
 * @create: 2021-12-21 2:31 下午
 */
@Mapper
public interface SysSupplierMapper extends BaseMapperX<SysSupplierRef> {

    /**
     * 获取所有供应商
     * @param page
     * @param reqVO
     * @return
     */
    IPage<SysOrgRespVO> selectOrgList(IPage<SysOrgRespVO> page, @Param("vo") SysOrgPageReqVO reqVO);

    /**
     * 获取下级部门分页列表
     * @param page
     * @param reqVO
     * @return
     */
    IPage<SysOrgRespVO> selLowerOrg(IPage<SysOrgRespVO> page, @Param("vo") SysSupPageReqVo reqVO);

    /**
     * 查询所有部门
     * @return
     */
    List<SysOrgRespVO> selectOrgAll();

    /**
     * 批量查询下级部门
     * @param list
     * @return
     */
    List<String> selByParentIds(@Param("list") List<String> list);

    /**
     * 根据部门id批量删除
     * @param list
     * @return
     */
    Integer deleteByOrgIds(@Param("list") List<String> list);


    /**
     * 根据parentId批量删除
     * @param list
     * @return
     */
    Integer deleteByParentIds(@Param("list") List<String> list);



}
