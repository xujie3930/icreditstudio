package com.micro.cloud.modules.system.supplier.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.cloud.modules.system.org.vo.OrgUpdateStatusReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgCreateReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgPageReqVO;
import com.micro.cloud.modules.system.org.vo.SysOrgRespVO;
import com.micro.cloud.modules.system.supplier.entity.SysSupplierRef;
import com.micro.cloud.modules.system.supplier.vo.SysSupLowerVo;
import com.micro.cloud.modules.system.supplier.vo.SysSupPageReqVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description: 供应商
 * @author: zlj
 * @create: 2021-12-21 2:16 下午
 */
public interface SysSupplierService extends IService<SysSupplierRef> {


    /**
     * 创建部门
     *
     * @param reqVO 部门信息
     * @return 部门编号
     */
    @Transactional(rollbackFor = Exception.class)
    String createSup(SysOrgCreateReqVO reqVO);

    /**
     * 获取所有部门信息分页
     * @param reqVO
     * @return
     */
    IPage<SysOrgRespVO> page(SysOrgPageReqVO reqVO);

    /**
     * 获取下级部门分页列表
     * @param reqVO
     * @return
     */
    IPage<SysOrgRespVO> selLowerOrg(SysSupPageReqVo reqVO);

    /**
     * 设置下级部门
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    Integer setLowerSup(SysSupLowerVo vo);

    /**
     * 获取所有部门
     * @return
     */
    List<SysOrgRespVO> getAllSup();


    /**
     * 删除部门
     *
     * @param ids 部门编号
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteDept(List<String> ids);

    /**
     * 删除下级部门
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    Integer deleteLowOrg(String id);


    /**
     * 批量禁用部门(包含子部门及其用户)
     *
     * @param reqVO 部门信息
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    Boolean changeStatusBatch(OrgUpdateStatusReqVO reqVO);

}
