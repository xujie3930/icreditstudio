package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.dict.web.request;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 
 *
 * @author 1
 */
@Data
public class CodeInfoEntitySaveRequest {
    private String id;
    @NotBlank(message = "10000001")
    private String codeName;
    @NotBlank(message = "10000001")
    private String codeType;
    @NotBlank(message = "10000001")
    private String codeValue;
    @Length(max = 200,message = "50002006")
    private String codeRemark;
    @NotBlank(message = "10000001")
    private String codeSort;
    private String deleteFlag;
}
