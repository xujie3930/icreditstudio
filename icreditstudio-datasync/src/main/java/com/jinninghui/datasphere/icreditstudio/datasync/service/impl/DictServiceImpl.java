package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.datasync.common.ResourceCodeBean;
import com.jinninghui.datasphere.icreditstudio.datasync.dto.DictQueryDTO;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.DictEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.DeleteFlagEnum;
import com.jinninghui.datasphere.icreditstudio.datasync.mapper.DictMapper;
import com.jinninghui.datasphere.icreditstudio.datasync.service.DictColumnService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.DictService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.AssociatedDictParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DictColumnSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DictQueryParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DictSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.AssociatedDictInfoResult;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictColumnResult;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictQueryResult;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictResult;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.utils.StringUtils;
import com.jinninghui.datasphere.icreditstudio.framework.utils.excel.ExcelUtil;
import com.jinninghui.datasphere.icreditstudio.framework.utils.excel.mode.DictColumnExcelMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, DictEntity> implements DictService {

    private final String DEFAULT_WORKSPACE_ID = "0";

    @Resource
    private DictMapper dictMapper;
    @Resource
    private DictColumnService dictColumnService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> save(DictSaveParam param) {
        checkDictParam(param);
        DictEntity dict = createDict(param);
        boolean isSaved = saveOrUpdate(dict);
        List<DictColumnSaveParam> saveParams = BeanCopyUtils.copy(param.getDictColumns(), DictColumnSaveParam.class);
        dictColumnService.saveBatch(dict.getId(), saveParams);
        return isSaved ? BusinessResult.success(isSaved) : BusinessResult.fail("", "保存失败");
    }

    private static void checkDictParam(DictSaveParam param) {
        if (StringUtils.isEmpty(param.getWorkspaceId())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000000.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000000.message);
        }
        if (!param.getEnglishName().matches("[a-zA-Z_]{0,50}")) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000081.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000081.message);
        }
        if (!param.getChineseName().matches("[\u4e00-\u9fa5]{0,50}")) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000082.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000082.message);
        }
        if (param.getDictDesc().length() > 250) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000083.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000083.message);
        }
    }

    private void checkDictId(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000080.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000080.message);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> del(String id) {
        checkDictId(id);
        dictColumnService.delBatchByDictId(DeleteFlagEnum.DELETED.getCode(), id);
        boolean isRemoved = dictMapper.delById(DeleteFlagEnum.DELETED.getCode(), id);
        return isRemoved ? BusinessResult.success(isRemoved) : BusinessResult.fail("", "删除失败");
    }

    @Override
    public BusinessResult<DictResult> getInfoById(String id) {
        DictResult dict = dictMapper.getInfoById(id);
        dict.setDictColumns(getColumnList(id));
        return BusinessResult.success(dict);
    }

    private List<DictColumnResult> getColumnList(String dictId) {
        checkDictId(dictId);
        return dictColumnService.getColumnListByDictId(dictId);
    }

    @Override
    public BusinessResult<List<DictColumnResult>> lookInfo(String dictId) {
        return BusinessResult.success(getColumnList(dictId));
    }

    @Override
    public BusinessResult<BusinessPageResult<DictQueryResult>> pageList(DictQueryParam param) {
        if (!DEFAULT_WORKSPACE_ID.equals(param.getWorkspaceId())) {
            param.setUserId(null);
        }
        DictQueryDTO dictQueryDTO = new DictQueryDTO();
        BeanCopyUtils.copyProperties(param, dictQueryDTO);
        dictQueryDTO.setPageNum((dictQueryDTO.getPageNum() - 1) * dictQueryDTO.getPageSize());
        long dictCount = dictMapper.countDict(dictQueryDTO);
        List<DictQueryResult> dictQuerys = dictMapper.pageList(dictQueryDTO);
        return BusinessResult.success(BusinessPageResult.build(dictQuerys, param, dictCount));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> update(DictSaveParam param) {
        checkDictId(param.getId());
        checkDictParam(param);
        DictEntity dict = new DictEntity();
        BeanCopyUtils.copyProperties(param, dict);
        dictColumnService.truthDelBatchByDictId(param.getId());
        List<DictColumnSaveParam> saveParams = BeanCopyUtils.copy(param.getDictColumns(), DictColumnSaveParam.class);
        dictColumnService.saveBatch(param.getId(), saveParams);
        boolean isUpdated = updateById(dict);
        return isUpdated ? BusinessResult.success(isUpdated) : BusinessResult.fail("", "更新失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> importDict(MultipartFile file, String dictSaveRequestJson) {
        DictSaveParam param = JSONObject.parseObject(dictSaveRequestJson).toJavaObject(DictSaveParam.class);
        checkDictParam(param);
        DictEntity dict = createDict(param);
        boolean isSaved = saveOrUpdate(dict);
        List<DictColumnExcelMode> dictColumnExcelList = ExcelUtil.readExcelFileData(file, 1, 1, DictColumnExcelMode.class);
        List<DictColumnSaveParam> saveParams = BeanCopyUtils.copy(dictColumnExcelList, DictColumnSaveParam.class);
        dictColumnService.saveBatch(dict.getId(), saveParams);
        return isSaved ? BusinessResult.success(isSaved) : BusinessResult.fail("", "导入失败");
    }

    private DictEntity createDict(DictSaveParam param) {
        DictEntity dict = new DictEntity();
        BeanCopyUtils.copyProperties(param, dict);
        dict.setCreateTime(new Date());
        dict.setDelFlag(DeleteFlagEnum.NOT_DELETED.getCode());
        return dict;
    }

    @Override
    public BusinessResult<List<AssociatedDictInfoResult>> associatedDict(AssociatedDictParam param) {
        QueryWrapper<DictEntity> wrapper = new QueryWrapper<>();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(param.getName())) {
            wrapper.like(DictEntity.CHINESE_NAME, param.getName());
        }
        List<DictEntity> list = list(wrapper);

        List<AssociatedDictInfoResult> results = null;
        results = list.stream()
                .filter(Objects::nonNull)
                .map(dict -> {
                    AssociatedDictInfoResult result = new AssociatedDictInfoResult();
                    result.setKey(dict.getId());
                    result.setName(dict.getChineseName());
                    return result;
                }).collect(Collectors.toList());
        return BusinessResult.success(Optional.ofNullable(results).orElse(Lists.newArrayList()));
    }
}