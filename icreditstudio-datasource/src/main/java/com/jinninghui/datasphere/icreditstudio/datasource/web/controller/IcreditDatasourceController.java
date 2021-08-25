package com.jinninghui.datasphere.icreditstudio.datasource.web.controller;


import com.jinninghui.datasphere.icreditstudio.datasource.entity.IcreditDatasourceEntity;
import com.jinninghui.datasphere.icreditstudio.datasource.service.IcreditDatasourceService;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.IcreditDatasourceDelParam;
import com.jinninghui.datasphere.icreditstudio.datasource.service.param.IcreditDatasourceSaveParam;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.IcreditDatasourceDelRequest;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.IcreditDatasourceEntityPageRequest;
import com.jinninghui.datasphere.icreditstudio.datasource.web.request.IcreditDatasourceSaveRequest;
import com.jinninghui.datasphere.icreditstudio.framework.log.Logable;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xujie
 * @since 2021-08-24
 */
@RestController
@RequestMapping("/datasource")
public class IcreditDatasourceController {

    @Autowired
    private IcreditDatasourceService datasourceService;


    /**
     * 新增数据源
     */
    @PostMapping("/save")
    @Logable
    public BusinessResult<Boolean> publish(@RequestHeader("x-userid") String userId, @RequestBody IcreditDatasourceSaveRequest request) {

        IcreditDatasourceSaveParam param = new IcreditDatasourceSaveParam();
        BeanCopyUtils.copyProperties(request, param);
        return datasourceService.saveDef(param);
    }

    /**
     * 更新数据源
     */
    @PostMapping("/update")
    @Logable
    public BusinessResult<Boolean> update(@RequestHeader("x-userid") String userId, @RequestBody IcreditDatasourceSaveRequest request) {

        IcreditDatasourceEntity entity = new IcreditDatasourceEntity();
        BeanCopyUtils.copyProperties(request, entity);
        return BusinessResult.success(datasourceService.updateById(entity));
    }

    /**
     * 删除数据源
     */
    @PostMapping("/delete")
    @Logable
    public BusinessResult<Boolean> deleteById(@RequestBody IcreditDatasourceDelRequest request) {
        IcreditDatasourceDelParam param = new IcreditDatasourceDelParam();
        BeanCopyUtils.copyProperties(request, param);
        return datasourceService.deleteById(param);
    }

    /**
     * 根据主键id查询数据源信息
     */
    @GetMapping("/info/{id}")
    @Logable
    public BusinessResult<IcreditDatasourceEntity> info(@PathVariable("id") String id){
        IcreditDatasourceEntity workspaceEntity = datasourceService.getById(id);
        return BusinessResult.success(workspaceEntity);
    }

    /**
     *
     */
    @PostMapping("/pageList")
    @Logable
    public BusinessResult<BusinessPageResult> pageList(@RequestBody IcreditDatasourceEntityPageRequest pageRequest){
        BusinessPageResult page = datasourceService.queryPage(pageRequest);
        return BusinessResult.success(page);
    }
}

