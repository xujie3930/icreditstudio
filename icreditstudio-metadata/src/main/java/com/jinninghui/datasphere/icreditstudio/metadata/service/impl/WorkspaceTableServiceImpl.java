package com.jinninghui.datasphere.icreditstudio.metadata.service.impl;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.metadata.entity.WorkspaceTableEntity;
import com.jinninghui.datasphere.icreditstudio.metadata.mapper.WorkspaceTableMapper;
import com.jinninghui.datasphere.icreditstudio.metadata.service.MetadataConnection;
import com.jinninghui.datasphere.icreditstudio.metadata.service.WorkspaceTableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.util.function.BiFunction;

/**
 * @author Peng
 */
@Service
public class WorkspaceTableServiceImpl extends ServiceImpl<WorkspaceTableMapper, WorkspaceTableEntity> implements WorkspaceTableService {

    @Resource
    private MetadataConnection connection;

    @Override
    public boolean authTable() {
        Object obj = null;
        /*smartCloseConn(connection, obj, (conn, r) -> {
            return null;
        });*/
        return false;
    }

    /**
     * 关闭连接
     *
     * @param connection 连接
     * @param r          做为function的r参数
     * @param function   处理函数
     * @param <R>
     * @param <T>
     * @return
     */
    public <R, T> T smartCloseConn(Connection connection, R r, BiFunction<Connection, R, T> function) {
        T apply;
        try {
            apply = function.apply(connection, r);
        } finally {
            IoUtil.close(connection);
        }
        return apply;
    }
}
