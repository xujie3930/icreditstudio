package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.log.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jinninghui.icreditdatasphere.icreditstudio.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.Serializable;

/**
 * @author 1
 */
@Data
@TableName("ge_audit_log")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class AuditLogEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String USER_NAME = "user_name";
    public static final String OPRATE_INFO = "oprate_info";
    public static final String OPRATE_TIME = "oprate_time";
    /**
     *
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "")
    private String id;
    /**
     * 增、删、改、查--C、R、U、D
     */
    @ApiModelProperty(value = "增、删、改、查--C、R、U、D")
    @ExcelProperty(value = "增、删、改、查--C、R、U、D")
    private String oprateType;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String userId;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String userName;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String oprateTime;
    /**
     * S....SUCCESS
     * F....FAILURE
     */
    @ApiModelProperty(value = "S....SUCCESS F....FAILURE")
    @ExcelProperty(value = "S....SUCCESS F....FAILURE")
    private String oprateResult;
    /**
     *
     */
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String oprateInfo;
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
