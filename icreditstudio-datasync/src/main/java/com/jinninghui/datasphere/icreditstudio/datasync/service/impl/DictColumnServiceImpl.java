package com.jinninghui.datasphere.icreditstudio.datasync.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.datasync.common.ResourceCodeBean;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.DictColumnEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.DeleteFlagEnum;
import com.jinninghui.datasphere.icreditstudio.datasync.mapper.DictColumnMapper;
import com.jinninghui.datasphere.icreditstudio.datasync.service.DictColumnService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DictColumnSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictColumnResult;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DictColumnServiceImpl extends ServiceImpl<DictColumnMapper, DictColumnEntity> implements DictColumnService {

    private static final String COLUMN_KEY_VALUE_REGEX = "[0-9a-zA-Z\u4e00-\u9fa5]{0,40}";

    @Autowired
    private DictColumnMapper dictColumnMapper;

    @Override
    public void delBatchByDictId(Integer delFlag, String dictId) {
        dictColumnMapper.delBatchByDictId(delFlag, dictId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(String dictId, List<DictColumnSaveParam> saveParams) {
        int size = saveParams.size();
        List<DictColumnEntity> dictColumns = new ArrayList<>();
        DictColumnEntity dictColumn;
        String columnKey = saveParams.get(0).getColumnKey();
        if(!columnKey.matches(COLUMN_KEY_VALUE_REGEX)){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000084.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000084.message);
        }
        boolean isExist = isExist(columnKey);
        if(isExist){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000079.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000079.message);
        }
        for (int i = 0; i < size; i++) {
            if(StringUtils.isEmpty(saveParams.get(i).getColumnKey())){
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000077.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000077.message);
            }
            if(!saveParams.get(i).getColumnKey().equals(columnKey)){
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000078.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000078.message);
            }
            if(!saveParams.get(i).getColumnValue().matches(COLUMN_KEY_VALUE_REGEX)){
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000085.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000085.message);
            }
            if(saveParams.get(i).getRemark().length() > 200){
                throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000086.code, ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000086.message);
            }
            dictColumn = new DictColumnEntity();
            dictColumn = BeanCopyUtils.copyProperties(saveParams.get(i), dictColumn);
            dictColumn.setDictId(dictId);
            dictColumn.setDelFlag(DeleteFlagEnum.NOT_DELETED.getCode());
            dictColumns.add(dictColumn);
        }
        saveBatch(dictColumns);
    }

    @Override
    public boolean isExist(String columnKey){
        String keyColumn = dictColumnMapper.findColumnByColumnKey(columnKey);
        return StringUtils.isEmpty(keyColumn) ? false : true;
    }

    @Override
    public List<DictColumnResult> getColumnListByDictId(String dictId) {
        return dictColumnMapper.getColumnListByDictId(dictId);
    }

    @Override
    public void truthDelBatchByDictId(String dictId) {
        dictColumnMapper.truthDelBatchByDictId(dictId);
    }
}
