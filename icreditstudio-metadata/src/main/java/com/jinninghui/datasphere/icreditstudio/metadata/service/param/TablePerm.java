package com.jinninghui.datasphere.icreditstudio.metadata.service.param;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class TablePerm {
    private String database;
    private String tableName;
    private List<Perm> perms;
    private List<Perm> unPerms;
}
