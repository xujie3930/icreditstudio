package com.jinninghui.datasphere.icreditstudio.metadata.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.jinninghui.datasphere.icreditstudio.framework.result.base.BaseObject;
import com.jinninghui.datasphere.icreditstudio.framework.result.base.BaseResult;
import lombok.Data;

import java.util.Date;

/**
 * @author Peng
 */
@Data
public class SyncBaseEntity extends BaseObject {
    //********************常量字段 start************************//

    public static final String ID = "ID";
    public static final String DELETE_FLAG = "DELETE_FLAG";
    public static final String CREATE_TIME = "CREATE_TIME";
    public static final String CREATE_USER_ID = "CREATE_USER_ID";
    public static final String LAST_UPDATE_TIME = "LAST_UPDATE_TIME";
    public static final String LAST_UPDATE_USER_ID = "LAST_UPDATE_USER_ID";

    //********************常量字段 endt************************//

    //********************转换方法 start***********************//

    public <T extends BaseResult> T  toResult(Class<T> targetClazz){
        return super.createAndCopyProperties(targetClazz);
    }

    //********************转换方法 end***********************//

  /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_user_id", fill = FieldFill.INSERT)
    private String createUserId;

    /**
     * 更新时间
     */
    @TableField(value = "last_update_time", fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateTime;

    /**
     * 更新人
     */
    @TableField(value = "last_update_user_id", fill = FieldFill.INSERT_UPDATE)
    private String lastUpdateUserId;

    /**
     * 删除标识【0：未删除，1：已删除】
     */
    @TableField(value = "delete_flag", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean deleteFlag;
}
