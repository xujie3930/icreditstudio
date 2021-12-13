package com.jinninghui.datasphere.icreditstudio.datasync.web;

import com.jinninghui.datasphere.icreditstudio.datasync.entity.DictColumnEntity;
import com.jinninghui.datasphere.icreditstudio.datasync.service.DictService;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DictQueryParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.param.DictSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictColumnResult;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictQueryResult;
import com.jinninghui.datasphere.icreditstudio.datasync.service.result.DictResult;
import com.jinninghui.datasphere.icreditstudio.datasync.web.request.DictQueryRequest;
import com.jinninghui.datasphere.icreditstudio.datasync.web.request.DictRequest;
import com.jinninghui.datasphere.icreditstudio.datasync.web.request.DictSaveRequest;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/datasync/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @PostMapping("/save")
    public BusinessResult<Boolean> save(@RequestBody DictSaveRequest request){
        DictSaveParam param = new DictSaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return dictService.save(param);
    }

    @PostMapping("/del")
    public BusinessResult<Boolean> delete(@RequestBody DictRequest request){
        return dictService.del(request.getId());
    }

    @PostMapping("/info")
    public BusinessResult<DictResult> getInfoById(@RequestBody DictRequest request){
        return dictService.getInfoById(request.getId());
    }

    @PostMapping("/update")
    public BusinessResult<Boolean> update(@RequestBody DictSaveRequest request){
        DictSaveParam param = new DictSaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return dictService.update(param);
    }

    @PostMapping("/pageList")
    public BusinessResult<BusinessPageResult<DictQueryResult>> pageList(@RequestBody DictQueryRequest request){
        DictQueryParam param = new DictQueryParam();
        BeanCopyUtils.copyProperties(request, param);
        return dictService.pageList(param);
    }

    @PostMapping("/lookInfo")
    public BusinessResult<List<DictColumnResult>> lookInfo(@RequestBody DictRequest request){
        return dictService.lookInfo(request.getId());
    }

    @PostMapping("/importDict")
    public BusinessResult<Boolean> importDict(HttpServletRequest request){
        return dictService.importDict(request);
    }

}
