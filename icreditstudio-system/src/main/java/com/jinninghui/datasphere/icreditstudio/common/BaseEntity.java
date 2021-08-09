package com.jinninghui.datasphere.icreditstudio.common;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Pengpai on 2021/5/24 11:16
 */
@Data
public class BaseEntity implements Serializable {
    public static final String ID = "ID";
    public static final String DELETE_FLAG = "DELETE_FLAG";
    public static final String CREATE_TIME = "CREATE_TIME";
    public static final String CREATE_USER_ID = "CREATE_USER_ID";
    public static final String LAST_UPDATE_TIME = "LAST_UPDATE_TIME";
    public static final String LAST_UPDATE_USER_ID = "LAST_UPDATE_USER_ID";

    /*@TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private String id;
    *//**
     * 删除标志 Y.已删除 N.未删除
     *//*
    @ApiModelProperty(value = "删除标志 Y.已删除 N.未删除")
    @ExcelProperty(value = "删除标志 Y.已删除 N.未删除")
    private String deleteFlag;
    *//**
     * 创建者id
     *//*
    @ApiModelProperty(value = "创建者id")
    private Long createTime;
    *//**
     * 创建时间
     *//*
    @ApiModelProperty(value = "创建时间")
    private String createUserId;
    *//**
     * 更新时间
     *//*
    @ApiModelProperty(value = "更新时间")
    private Long lastUpdateTime;
    *//**
     * 更新者id
     *//*
    @ApiModelProperty(value = "更新者id")
    private String lastUpdateUserId;*/

}
