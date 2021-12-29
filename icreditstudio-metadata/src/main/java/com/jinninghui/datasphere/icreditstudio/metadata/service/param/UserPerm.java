package com.jinninghui.datasphere.icreditstudio.metadata.service.param;

import lombok.Data;

import java.util.List;

/**
 * @author Peng
 */
@Data
public class UserPerm {

    private String userName;
    private List<TablePerm> tablePerms;
}
