package com.jinninghui.datasphere.icreditstudio.metadata.service.impl;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.metadata.common.ResourceCodeBean;
import com.jinninghui.datasphere.icreditstudio.metadata.entity.WorkspaceTableEntity;
import com.jinninghui.datasphere.icreditstudio.metadata.mapper.WorkspaceTableMapper;
import com.jinninghui.datasphere.icreditstudio.metadata.service.WorkspaceTableService;
import com.jinninghui.datasphere.icreditstudio.metadata.service.param.Perm;
import com.jinninghui.datasphere.icreditstudio.metadata.service.param.TablePerm;
import com.jinninghui.datasphere.icreditstudio.metadata.service.param.UserPerm;
import com.jinninghui.datasphere.icreditstudio.metadata.web.request.WorkspaceTableListParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @author Peng
 */
@Slf4j
@Service
public class WorkspaceTableServiceImpl extends ServiceImpl<WorkspaceTableMapper, WorkspaceTableEntity> implements WorkspaceTableService {

    @Override
    public boolean authTable(List<UserPerm> userPerms, Connection conn) {
        auth(userPerms, conn);
        return true;
    }

    @Override
    public boolean unAuthTable(List<UserPerm> userPerms, Connection conn) {
        unAuth(userPerms, conn);
        return true;
    }

    public void auth(List<UserPerm> userPerms, Connection conn) {
        Statement state = null;
        try {
            state = conn.createStatement();
            for (UserPerm userPerm : userPerms) {
                List<TablePerm> tablePerms = userPerm.getTablePerms();
                for (TablePerm tablePerm : tablePerms) {
                    List<Perm> perms = tablePerm.getPerms();
                    for (Perm perm : perms) {
                        String sql = "grant  " + perm.getPerm() + " on table " + tablePerm.getDatabase() + "." + tablePerm.getTableName() + " to user " + userPerm.getUserName();
                        log.info("授权语句：" + sql);
                        try {
                            state.execute(sql);
                        } catch (Exception e) {
                            log.error("授权失败", e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_80000007.getCode());
        } finally {
            IoUtil.close(state);
        }
    }

    public void unAuth(List<UserPerm> userPerms, Connection conn) {
        Statement state = null;
        try {
            state = conn.createStatement();
            for (UserPerm userPerm : userPerms) {
                List<TablePerm> tablePerms = userPerm.getTablePerms();
                for (TablePerm tablePerm : tablePerms) {
                    List<Perm> perms = tablePerm.getUnPerms();
                    for (Perm perm : perms) {
                        String sql = "revoke  " + perm.getPerm() + " on table " + tablePerm.getDatabase() + "." + tablePerm.getTableName() + " from user " + userPerm.getUserName();
                        log.info("解除授权语句：" + sql);
                        try {
                            state.execute(sql);
                        } catch (Exception e) {
                            log.error("解除权限失败", e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_80000008.getCode());
        } finally {
            IoUtil.close(state);
        }
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

    @Override
    public List<WorkspaceTableEntity> getWorkspaceTableList(WorkspaceTableListParam param) {
        QueryWrapper<WorkspaceTableEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getWorkspaceId())) {
            wrapper.eq(WorkspaceTableEntity.WORKSPACE_ID, param.getWorkspaceId());
        }
        wrapper.eq(WorkspaceTableEntity.DELETE_FLAG, 0);
        return list(wrapper);
    }
}
