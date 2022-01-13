package com.jinninghui.datasphere.icreditstudio.workspace.web.request;

import lombok.Data;

/**
 * @author xujie
 * @description 判断工作空间是否存在
 * @create 2021-08-23 14:32
 **/
@Data
public class WorkspaceHasExistRequest {
    private String name;
    private String id;

    public WorkspaceHasExistRequest() {
    }

    public WorkspaceHasExistRequest(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
