package com.jinninghui.datasphere.icreditstudio.workspace.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xujie
 * @description 工作空间模板类
 * @create 2021-08-20 15:06
 **/
@Data
public class IcreditWorkspaceSaveRequest {
    private String id;//主键id
    @NotBlank(message = "工作空间名称不能为空")
    @Length(max = 15, message = "工作空间名称不能超过15个字符")
    private String name;//空间名称
    private String descriptor;//空间描述
    private int status = 0;//空间状态
    private String director;//负责人
    private String createUser;//创建人
    private List<WorkspaceMember> memberList = new ArrayList<>();
}
