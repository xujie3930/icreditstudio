package com.jinninghui.datasphere.icreditstudio.system.modules.system.information.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jinninghui.datasphere.icreditstudio.system.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.Serializable;

/**
 * @author 1
 */
@Data
@TableName("ge_information")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class InformationEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String INFO_TITLE = "INFO_TITLE";
    public static final String INFO_TYPE = "INFO_TYPE";
    public static final String SENDER_ID = "SENDER_ID";
    public static final String BROADCAST_FLAG = "BROADCAST_FLAG";
    public static final String SEND_TIME = "SEND_TIME";

    /**
     *
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "")
    private String id;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String infoTitle;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String infoContent;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private Long sendTime;
    /**
     * 系统......S (system)
     * 通知......N(notice）
     * 预警......W(warning)
     */
    @ApiModelProperty(value = "系统......S (system)通知......N(notice）预警......W(warning)")
    @ExcelProperty(value = "系统......S (system)通知......N(notice）预警......W(warning)")
    private String infoType;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String senderId;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long createTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String createUserId;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private Long lastUpdateTime;
    /**
     *
     */
    @ApiModelProperty(value = "")
    private String lastUpdateUserId;
    /**
     * Y....yes
     * N...no
     */
    @ApiModelProperty(value = "Y....yes N...no")
    @ExcelProperty(value = "Y....yes N...no")
    private String deleteFlag;
    /**
     * S.....单记录
     * B.....广播
     */
    @ApiModelProperty(value = "S.....单记录 B.....广播")
    @ExcelProperty(value = "S.....单记录 B.....广播")
    private String broadcastFlag;

}
