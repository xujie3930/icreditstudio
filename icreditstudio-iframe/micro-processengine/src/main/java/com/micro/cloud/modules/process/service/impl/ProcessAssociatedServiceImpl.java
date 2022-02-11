package com.micro.cloud.modules.process.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.cloud.modules.process.dal.dataobject.ProcessAssociated;
import com.micro.cloud.modules.process.dal.mapper.ProcessAssociatedMapper;
import com.micro.cloud.modules.process.param.SaveAssociateProcessParam;
import com.micro.cloud.modules.process.service.ProcessAssociatedService;
import com.micro.cloud.snowflake.sequence.SequenceService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈〉
 *
 * @author roy
 * @create 2021/12/18
 * @since 1.0.0
 */
@Service("processAssociatedService")
public class ProcessAssociatedServiceImpl
    extends ServiceImpl<ProcessAssociatedMapper, ProcessAssociated>
    implements ProcessAssociatedService {

  private final Logger logger = LoggerFactory.getLogger(ProcessAssociatedServiceImpl.class);

  @Autowired private SequenceService sequenceService;

  /**
   * 保存关联数据
   *
   * @param param 关联设置请求参数
   * @return 主键id
   */
  @Override
  public Boolean saveData(SaveAssociateProcessParam param) {
    logger.info("##### data:{}", param.getAssociateProcessList());
    List<ProcessAssociated> entryList = new ArrayList<>();
    Optional.ofNullable(param.getAssociateProcessList())
        .ifPresent(
            params -> {
              params.stream()
                  .forEach(
                      data -> {
                        ProcessAssociated processAssociated = new ProcessAssociated();
                        processAssociated.setProcessAssociateId(
                            sequenceService.nextStringValue(null));
                        processAssociated.setProcessInstanceId(param.getProcessInstanceId());
                        processAssociated.setAssociateProcessInstanceId(
                            data.getProcessInstanceId());
                        processAssociated.setCondition(JSONUtil.toJsonStr(data));
                        entryList.add(processAssociated);
                      });
            });
    return saveBatch(entryList);
  }

    @Override
    public Boolean delData(List<String> ids) {
        return removeByIds(ids);
    }
}
