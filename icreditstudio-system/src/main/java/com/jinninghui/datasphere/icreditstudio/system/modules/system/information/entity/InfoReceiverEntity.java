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
@TableName("ge_info_receiver")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class InfoReceiverEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String INFO_ID = "INFO_ID";
    public static final String RECEIVER_ID = "RECEIVER_ID";
    public static final String READ_STATUS = "READ_STATUS";

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
    private String infoId;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String receiverId;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String readTime;
    /**
     * Y...YES
     * N...NO
     */
    @ApiModelProperty(value = "Y...YES N...NO")
    @ExcelProperty(value = "Y...YES N...NO")
    private String readStatus;
    /**
     * Y...YES
     * N...NO
     */
    @ApiModelProperty(value = "Y...YES N...NO")
    @ExcelProperty(value = "Y...YES N...NO")
    private String deleteFlag;
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

}
