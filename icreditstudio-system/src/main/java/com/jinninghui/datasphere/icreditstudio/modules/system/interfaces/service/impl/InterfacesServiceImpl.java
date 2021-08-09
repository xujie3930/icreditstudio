package com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.modules.system.allinterface.param.InterfaceAuthParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.allinterface.result.InterfaceAuthResult;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.entity.InterfacesEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.mapper.InterfacesDao;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.service.InterfacesService;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.service.param.InterfacesDelParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.web.request.InterfacesEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.web.request.InterfacesEntitySaveParam;
import com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.web.result.InterfacesInfoExpert;
import com.jinninghui.datasphere.icreditstudio.modules.system.org.web.result.ExpertInfoResult;
import com.hashtech.businessframework.exception.interval.AppException;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.Query;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.sequence.api.SequenceService;
import com.hashtech.businessframework.utils.excel.EasyExcelUtil;
import com.hashtech.businessframework.utils.excel.ExcelUtil;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("interfacesService")
@Slf4j
public class InterfacesServiceImpl extends ServiceImpl<InterfacesDao, InterfacesEntity> implements InterfacesService {


    @Autowired
    private InterfacesDao interfacesDao;
    @Autowired
    private SequenceService generalSequence;

    @Override
    public BusinessPageResult queryPage(InterfacesEntityPageRequest pageRequest) {
        QueryWrapper<InterfacesEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(pageRequest.getName())) {
            wrapper.like(InterfacesEntity.NAME, pageRequest.getName());
        }
        wrapper.orderByDesc(InterfacesEntity.CREATE_TIME);
        IPage<InterfacesEntity> page = this.page(new Query<InterfacesEntity>().getPage(pageRequest), wrapper);

        return BusinessPageResult.build(page, pageRequest);
    }

    @Override
    public List<InterfaceAuthResult> getUserAuthInterfaceIdList(InterfaceAuthParam param) {

        return interfacesDao.getUserAuthInterfaceIdList(param);
    }

    @Override
    public BusinessResult<?> exportExcel(HttpServletRequest request, HttpServletResponse response, InterfacesEntity interfaces) {

        try {
            List<InterfacesInfoExpert> interfacesInfoExperts = interfacesDao.exportExcel(interfaces);
            ExcelUtil.exportExcel(response, "接口列表导出", interfacesInfoExperts, InterfacesInfoExpert.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return BusinessResult.fail("", "接口列表导出失败");
        }
        return BusinessResult.success("接口列表导出成功");
    }

    @Override
    public BusinessResult<?> importExcel(HttpServletRequest request, HttpServletResponse response, Class<InterfacesInfoExpert> interfacesEntityClass) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        List<String> nameList = new ArrayList<>();
        ExpertInfoResult expertInfoResult = new ExpertInfoResult();
        List<InterfacesEntity> passList = new ArrayList<>();
        List<Object> noPassList = new ArrayList<>();

        // 获取所有的部门信息
        List<InterfacesEntity> interfacesEntityList = this.list();
        if (CollectionUtils.isNotEmpty(interfacesEntityList)) {
            nameList = interfacesEntityList.stream().map(InterfacesEntity::getName).collect(Collectors.toList());
        }
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 获取上传文件对象
            MultipartFile file = entity.getValue();
            try {
                List<T> importExcelList = EasyExcelUtil.syncReadModel(file.getInputStream(), interfacesEntityClass, 0, 1);
                List<InterfacesInfoExpert> infoExperts = BeanCopyUtils.copy(importExcelList, InterfacesInfoExpert.class);

                // 去除导入时 接口名称的重复
                Map<String, List<InterfacesInfoExpert>> interfacesNameGroupBy = infoExperts
                        .stream().filter(f -> f.getName() != null)
                        .collect(Collectors.groupingBy(InterfacesInfoExpert::getName));
                interfacesNameGroupBy.forEach((key, value) -> {
                    if (value.size() > 1) {
                        value.forEach(f -> {
                            String format = String.format("导入接口名称[%S]重复!", f.getName());
                            f.setErrorMsg(format);
                            noPassList.add(f);
                        });
                        infoExperts.removeAll(value);
                    }
                });

                for (InterfacesInfoExpert interfacesInfoExpert : infoExperts) {
                    //校验
                    if (!importOrgCheck(nameList, noPassList, interfacesInfoExpert)) {
                        continue;
                    }
                    // 通过保存
                    InterfacesEntity organizationEntity = BeanCopyUtils.copyProperties(interfacesInfoExpert, InterfacesEntity.class);
                    organizationEntity.setInterfaceId(generalSequence.nextValueString());
                    organizationEntity.setNeedAuth(interfacesInfoExpert.getNeedAuthName() == null ? 0 :
                            "是".equals(interfacesInfoExpert.getNeedAuthName()) ? 1 : 0);
                    organizationEntity.setUriType(interfacesInfoExpert.getNeedAuthName() == null ? "0" :
                            "通配符".equals(interfacesInfoExpert.getUriType()) ? "1" : "0");
                    passList.add(organizationEntity);
                }
                expertInfoResult.setNoPassList(noPassList);
                expertInfoResult.setErrorCount(noPassList.size());
                expertInfoResult.setSuccessCount(passList.size());

                this.saveBatch(passList);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                //手动开启事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return BusinessResult.fail("00", "文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return BusinessResult.success(expertInfoResult);
    }

    private boolean importOrgCheck(List<String> nameList, List<Object> noPassList, InterfacesInfoExpert interfacesInfoExpert) {


        if (StringUtils.isEmpty(interfacesInfoExpert.getName())) {
            interfacesInfoExpert.setErrorMsg("接口名称不能为空");
            noPassList.add(interfacesInfoExpert);
            return false;
        }

        if (nameList.contains(interfacesInfoExpert.getName())) {
            String format = String.format("接口名称[%S]已存在!", interfacesInfoExpert.getName());
            interfacesInfoExpert.setErrorMsg(format);
            noPassList.add(interfacesInfoExpert);
            return false;
        }

        return true;
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> save(InterfacesEntitySaveParam param) {
        InterfacesEntity entity = BeanCopyUtils.copyProperties(param, InterfacesEntity.class);
        entity.setInterfaceId(generalSequence.nextValueString());
        save(entity);
        return BusinessResult.success(true);
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> update(InterfacesEntitySaveParam param) {
        String interfaceId = param.getInterfaceId();
        if (StringUtils.isBlank(interfaceId)) {
            throw new AppException("50009338");
        }
        InterfacesEntity entity = BeanCopyUtils.copyProperties(param, InterfacesEntity.class);
        updateById(entity);
        return BusinessResult.success(true);
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> delete(InterfacesDelParam param) {
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            removeByIds(param.getIds());
        }
        return BusinessResult.success(true);
    }
}
