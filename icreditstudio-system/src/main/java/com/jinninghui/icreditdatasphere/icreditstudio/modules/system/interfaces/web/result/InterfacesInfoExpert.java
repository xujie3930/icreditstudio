package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.interfaces.web.result;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.Serializable;

/**
 * @author hzh
 * @description
 * @date 2021/3/11 10:07
 */
@Data
@ExcelIgnoreUnannotated
@HeadFontStyle(fontHeightInPoints = 20)
@ColumnWidth(25)
@ContentStyle(horizontalAlignment = HorizontalAlignment.CENTER, borderTop = BorderStyle.THIN, borderBottom = BorderStyle.THIN, borderLeft = BorderStyle.THIN, borderRight = BorderStyle.THIN)
public class InterfacesInfoExpert  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    @ExcelProperty(value = "名称")
    private String name;
    /**
     * 是否需要鉴权，0不需要鉴权，1需要鉴权
     */
    @ApiModelProperty(value = "是否需要鉴权，0不需要鉴权，1需要鉴权")
    @ExcelProperty(value = "是否鉴权")
    private String needAuthName;
    /**
     * 支持的鉴权方式，字符串，以下划线分隔。如：cert,token,cert_token
     */
    @ApiModelProperty(value = "支持的鉴权方式，字符串，以下划线分隔。如：cert,token,cert_token")
    @ExcelProperty(value = "鉴权方式")
    private String supportAuthType;
    /**
     * 接口地址, 通配符 0:接口地址，1：通配符
     */
    @ApiModelProperty(value = "接口地址, 通配符 0:接口地址，1：通配符")
    @ExcelProperty(value = "类型")
    private String uriType;
    /**
     * GET，POST，PUT，DELETE
     */
    @ApiModelProperty(value = "GET，POST，PUT，DELETE")
    @ExcelProperty(value = "请求类型")
    private String method;
    /**
     * 接口归属系统模块
     */
    @ApiModelProperty(value = "接口归属系统模块")
    @ExcelProperty(value = "接口归属模块")
    private String module;
    /**
     * 接口URI
     */
    @ApiModelProperty(value = "接口地址")
    @ExcelProperty(value = "接口地址")
    private String uri;
    /**
     * 说明
     */
    @ApiModelProperty(value = "备注")
    @ExcelProperty(value = "备注")
    private String remark;
    /**
     * 是否需要鉴权，0不需要鉴权，1需要鉴权
     */
    @ApiModelProperty(value = "是否需要鉴权，0不需要鉴权，1需要鉴权")
    private Integer needAuth;

    /**
     *  导入校验的错误信息
     */
    @ApiModelProperty(value = "导入校验的错误信息")
    private String errorMsg;

}
