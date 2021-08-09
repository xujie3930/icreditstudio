package com.jinninghui.datasphere.icreditstudio.modules.system.interfaces.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jinninghui.datasphere.icreditstudio.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @author hzh
 */
@Data
@TableName("ge_interfaces")
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 12)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class InterfacesEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String NAME = "NAME";
    public static final String INTERFACE_ID = "INTERFACE_ID";
    /**
     * 接口id数据库自增
     */
    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "")
    @ExcelProperty(value = "")
    private String interfaceId;
    /**
     * 接口URI
     */
    @ApiModelProperty(value = "接口URI")
    @ExcelProperty(value = "接口URI")
    private String uri;
    /**
     * GET，POST，PUT，DELETE
     */
    @ApiModelProperty(value = "GET，POST，PUT，DELETE")
    @ExcelProperty(value = "GET，POST，PUT，DELETE")
    private String method;
    /**
     * 接口归属系统模块
     */
    @ApiModelProperty(value = "接口归属系统模块")
    @ExcelProperty(value = "接口归属系统模块")
    private String module;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @ExcelProperty(value = "名称")
    private String name;
    /**
     * 说明
     */
    @ApiModelProperty(value = "说明")
    @ExcelProperty(value = "说明")
    @Length(max = 200, message = "50000011")
    private String remark;
    /**
     * 是否需要鉴权，0不需要鉴权，1需要鉴权
     */
    @ApiModelProperty(value = "是否需要鉴权，0不需要鉴权，1需要鉴权")
    @ExcelProperty(value = "是否需要鉴权，0不需要鉴权，1需要鉴权")
    private Integer needAuth;
    /**
     * 支持的鉴权方式，字符串，以下划线分隔。如：cert,token,cert_token
     */
    @ApiModelProperty(value = "支持的鉴权方式，字符串，以下划线分隔。如：cert,token,cert_token")
    @ExcelProperty(value = "支持的鉴权方式，字符串，以下划线分隔。如：cert,token,cert_token")
    private String supportAuthType;
    /**
     * 接口地址, 通配符 0:接口地址，1：通配符
     */
    @ApiModelProperty(value = "接口地址, 通配符 0:接口地址，1：通配符")
    @ExcelProperty(value = "接口地址, 通配符 0:接口地址，1：通配符")
    private String uriType;

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
