package com.jinninghui.datasphere.icreditstudio.datasync.service.task.reader.mysql;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jinninghui.datasphere.icreditstudio.datasync.common.ResourceCodeBean;
import com.jinninghui.datasphere.icreditstudio.datasync.entity.SyncWidetableEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.feign.DatasourceFeign;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictInfo;
import com.jinninghui.datasphere.icreditstudio.datasync.service.task.reader.AbstractDataxReaderHandler;
import com.jinninghui.datasphere.icreditstudio.datasync.service.task.reader.ReaderConfigParam;
import com.jinninghui.datasphere.icreditstudio.framework.common.enums.DialectEnum;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Peng
 */
@Data
@Component
public class MySqlReader extends AbstractDataxReaderHandler {
    /**
     * 需要字典转换的列
     */
    private Map<String, String> needTransferColumns;
    /**
     * 字典列表
     */
    private List<DictInfo> transferDict;
    /**
     * mysqlreader配置信息
     */
    private MysqlReaderConfigParam configParam;

    @Resource
    private DatasourceFeign datasourceFeign;

    @Override
    public Map<String, Object> getReaderEntity() {
        Map<String, Object> reader = new HashMap<>();
        reader.put("name", "mysqlreader");

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("needTransferColumns", getNeedTransferColumns());
        parameter.put("transferDict", getTransferDict());
        parameter.put("username", configParam.getUsername());
        parameter.put("password", configParam.getPassword());

        List<Map<String, List<String>>> connections = Lists.newArrayList();
        Map<String, List<String>> connection = Maps.newHashMap();
        connection.put("querySql", Lists.newArrayList(configParam.getQuerySql()));
        connection.put("jdbcUrl", Lists.newArrayList(configParam.getJdbcUrl()));
        connections.add(connection);
        parameter.put("connection", connections);
        reader.put("parameter", parameter);
        return reader;
    }

    @Override
    public String getDialect() {
        return DialectEnum.MYSQL.getDialect();
    }

    @Override
    public void preSetConfigParam(SyncWidetableEntity widetableEntity) {
        String datasourceId = widetableEntity.getDatasourceId();
        if (StringUtils.isBlank(datasourceId)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000003.getCode());
        }
        BusinessResult<ReaderConfigParam> datasourceJdbcInfo = datasourceFeign.getDatasourceJdbcInfo(datasourceId);
        MysqlReaderConfigParam data = null;
        if (datasourceJdbcInfo.isSuccess()) {
//            data = (MysqlReaderConfigParam) datasourceJdbcInfo.getData();
            data = BeanCopyUtils.copyProperties(datasourceJdbcInfo, MysqlReaderConfigParam.class);
            data.setQuerySql(widetableEntity.getSqlStr());
        }
        configParam = data;
    }

    public Map<String, String> getNeedTransferColumns() {
        if (MapUtils.isEmpty(needTransferColumns)) {
            return Maps.newHashMap();
        }
        return needTransferColumns;
    }

    public List<DictInfo> getTransferDict() {
        if (CollectionUtils.isEmpty(transferDict)) {
            return Lists.newArrayList();
        }
        return transferDict;
    }
}
