package com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.result;

import lombok.Data;

import java.util.List;

@Data
public class DictResult {

    private String id;
    private String englishName;
    private String chineseName;
    private String dictDesc;
    private List<DictColumnResult> dictColumns;

}
