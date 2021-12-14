package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.datasync.common.ResourceCodeBean;
import com.jinninghui.datasphere.icreditstudio.datasync.dto.DictQueryDTO;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.DictEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.DeleteFlagEnum;
import com.jinninghui.datasphere.icreditstudio.datasync.mapper.DictMapper;
import com.jinninghui.datasphere.icreditstudio.datasync.service.DictColumnService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.DictService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DictColumnSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DictQueryParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DictSaveParam;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, DictEntity> implements DictService {

    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private DictColumnService dictColumnService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> save(DictSaveParam param) {
        DictEntity dict = createDict(param);
        boolean isSaved = saveOrUpdate(dict);
        List<DictColumnSaveParam> saveParams = BeanCopyUtils.copy(param.getDictColumns(), DictColumnSaveParam.class);
        dictColumnService.saveBatch(dict.getId(), saveParams);
        return isSaved ? BusinessResult.success(isSaved) : BusinessResult.fail("", "保存失败");
    }

    private void checkDictId(String id){
        if(StringUtils.isEmpty(id)){
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

    private List<DictColumnResult> getColumnList(String dictId){
        checkDictId(dictId);
        return dictColumnService.getColumnListByDictId(dictId);
    }

    @Override
    public BusinessResult<List<DictColumnResult>> lookInfo(String dictId) {
        return BusinessResult.success(getColumnList(dictId));
    }

    @Override
    public BusinessResult<BusinessPageResult<DictQueryResult>> pageList(DictQueryParam param) {
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
    public BusinessResult<Boolean> importDict(MultipartFile file, DictSaveParam param) {
        DictEntity dict = createDict(param);
        boolean isSaved = saveOrUpdate(dict);
        List<DictColumnExcelMode> dictColumnExcelList = ExcelUtil.readExcelFileData(file, 1, 1, DictColumnExcelMode.class);
        List<DictColumnSaveParam> saveParams = BeanCopyUtils.copy(dictColumnExcelList, DictColumnSaveParam.class);
        dictColumnService.saveBatch(dict.getId(), saveParams);
        return isSaved ? BusinessResult.success(isSaved) : BusinessResult.fail("", "导入失败");
    }

    private DictEntity createDict(DictSaveParam param){
        DictEntity dict = new DictEntity();
        BeanCopyUtils.copyProperties(param, dict);
        dict.setCreateTime(new Date());
        dict.setDelFlag(0);
        return dict;
    }

}