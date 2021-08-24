package com.jinninghui.datasphere.icreaditstudio.workspace.common;

import lombok.Data;

/**
 * @author xujie
 * @description 描述共有属性
 * @create 2021-08-19 14:34
 **/
@Data
public class BaseEntity {

    public static final String ID = "ID";
    public static final String DELETE_FLAG = "DELETE_FLAG";//Y or N
    public static final String CREATE_TIME = "CREATE_TIME";
    public static final String CREATE_USER_ID = "CREATE_USER_ID";
    public static final String LAST_UPDATE_TIME = "LAST_UPDATE_TIME";
    public static final String LAST_UPDATE_USER_ID = "LAST_UPDATE_USER_ID";
}
