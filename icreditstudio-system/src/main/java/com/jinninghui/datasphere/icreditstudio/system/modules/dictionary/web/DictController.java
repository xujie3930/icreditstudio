package com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.web;

import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.result.AssociatedDictInfoResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.result.DictColumnResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.result.DictQueryResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.result.DictResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.DictService;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.param.AssociatedDictParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.param.DictQueryParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.service.param.DictSaveParam;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.web.request.AssociatedDictRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.web.request.DictQueryRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.web.request.DictRequest;
import com.jinninghui.datasphere.icreditstudio.system.modules.dictionary.web.request.DictSaveRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController {

    @Resource
    private DictService dictService;

    @PostMapping("/save")
    public BusinessResult<Boolean> save(@RequestBody DictSaveRequest request) {
        DictSaveParam param = new DictSaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return dictService.save(param);
    }

    @PostMapping("/del")
    public BusinessResult<Boolean> delete(@RequestBody DictRequest request) {
        return dictService.del(request.getId());
    }

    @PostMapping("/info")
    public BusinessResult<DictResult> getInfoById(@RequestBody DictRequest request) {
        return dictService.getInfoById(request.getId());
    }

    @PostMapping("/update")
    public BusinessResult<Boolean> update(@RequestBody DictSaveRequest request) {
        DictSaveParam param = new DictSaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return dictService.update(param);
    }

    @PostMapping("/pageList")
    public BusinessResult<BusinessPageResult<DictQueryResult>> pageList(@RequestBody DictQueryRequest request) {
        DictQueryParam param = new DictQueryParam();
        BeanCopyUtils.copyProperties(request, param);
        return dictService.pageList(param);
    }

    @PostMapping("/lookInfo")
    public BusinessResult<List<DictColumnResult>> lookInfo(@RequestBody DictRequest request) {
        return dictService.lookInfo(request.getId());
    }

    @PostMapping("/importDict")
    public BusinessResult<Boolean> importDict(@RequestPart("file") MultipartFile file, @RequestParam("dictSaveRequest") String dictSaveRequest) {
        return dictService.importDict(file, dictSaveRequest);
    }

    @PostMapping("/associatedDict")
    public BusinessResult<List<AssociatedDictInfoResult>> associatedDict(@RequestBody AssociatedDictRequest request) {
        AssociatedDictParam param = new AssociatedDictParam();
        BeanCopyUtils.copyProperties(request, param);
        return dictService.associatedDict(param);
    }
}
