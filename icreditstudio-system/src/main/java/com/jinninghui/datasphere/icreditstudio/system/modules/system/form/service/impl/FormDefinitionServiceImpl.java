package com.jinninghui.datasphere.icreditstudio.system.modules.system.form.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.jinninghui.datasphere.icreditstudio.framework.exception.interval.AppException;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessPageResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.BusinessResult;
import com.jinninghui.datasphere.icreditstudio.framework.result.Query;
import com.jinninghui.datasphere.icreditstudio.framework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.framework.validate.BusinessParamsValidate;
import com.jinninghui.datasphere.icreditstudio.system.common.enums.FormStatusEnum;
import com.jinninghui.datasphere.icreditstudio.system.common.enums.PermFlagEnum;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.entity.FormDefinitionEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.entity.FormElementEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.entity.FormPermEntity;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.mapper.FormDefinitionMapper;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.service.FormDefinitionService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.service.FormElementService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.service.FormHiDefinitionService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.service.FormPermService;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.service.param.*;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.service.result.FormDefinitionResult;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request.ElementPermVo;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request.FormElementVo;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request.FormModelVo;
import com.jinninghui.datasphere.icreditstudio.system.modules.system.form.web.request.UserElePerm;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service("formDefinitionService")
public class FormDefinitionServiceImpl extends ServiceImpl<FormDefinitionMapper, FormDefinitionEntity> implements FormDefinitionService {

    @Autowired
    private FormDefinitionMapper formDefinitionMapper;
    @Autowired
    private FormElementService formElementService;
    @Autowired
    private FormPermService formPermService;
    @Autowired
    private FormHiDefinitionService formHiDefinitionService;
    @Autowired
    @Qualifier("executor")
    private ThreadPoolExecutor executor;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public BusinessResult<BusinessPageResult> queryPage(FormDefinitionPageParam param) {
        FormDefinitionConditionParam build = FormDefinitionConditionParam.builder()
                .formName(param.getFormName())
                .formStatus(FormStatusEnum.find(param.getFormStatus()))
                .formVersion(param.getFormVersion())
                .build();

        IPage<FormDefinitionEntity> page = this.page(
                new Query<FormDefinitionEntity>().getPage(param),
                queryWrapper(build)
        );

        return BusinessResult.success(BusinessPageResult.build(page, param));
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> saveDef(FormDefinitionSaveParam param) {
        String id = param.getId();
        String formStatus = param.getFormStatus();
        boolean change = param.isChange();
        FormModelVo modelEditorJson = param.getModelEditorJson();
        FormStatusEnum formStatusEnum = FormStatusEnum.find(formStatus);

        FormDefinitionEntity defEntity = new FormDefinitionEntity();
        defEntity.setId(id);
        defEntity.setFormName(param.getFormName());
        defEntity.setFormDesc(param.getFormDesc());
        defEntity.setDefJson(modelEditorJson.getConfig());
        defEntity.setFormStatus(formStatusEnum.getCode());
        defEntity.setUserId(param.getUserId());
        defEntity.setFormVersion(updateVersion(id, change, formStatusEnum));
        if (FormStatusEnum.P.equals(formStatusEnum)) {
            defEntity.setFormPublishTime(System.currentTimeMillis());
        }
        saveOrUpdate(defEntity);

        List<FormElementVo> componentsList = modelEditorJson.getList();
        List<FormElementEntity> transfer = transfer(defEntity.getId(), componentsList);
        if (CollectionUtils.isNotEmpty(transfer)) {
            formElementService.saveOrUpdateBatch(transfer);
        }

        Set<String> idsByFormId = formPermService.getIdsByFormId(param.getId());
        if (CollectionUtils.isNotEmpty(idsByFormId)) {
            formPermService.removeByIds(idsByFormId);
        }
        List<ElementPermVo> authList = modelEditorJson.getAuthList();
        List<FormPermEntity> permEntities = transfer(authList, defEntity.getId());
        if (CollectionUtils.isNotEmpty(permEntities)) {
            formPermService.saveOrUpdateBatch(permEntities);
        }
        return BusinessResult.success(true);
    }

    private String updateVersion(String id, boolean change, FormStatusEnum status) {
        String version = null;
        if ((StringUtils.isBlank(id) && FormStatusEnum.D.equals(status)) ||
                (StringUtils.isBlank(id) && FormStatusEnum.P.equals(status))) {
            version = initVersionCache(id);
        }
        if (StringUtils.isNotBlank(id) && FormStatusEnum.P.equals(status) && change) {
            version = incVersionCacheAndGet(id);
        }
        return version;
    }

    private String initVersionCache(String formId) {
        String key = formId + "_version";
        stringRedisTemplate.opsForValue().set(key, "1");
        return "1.0";
    }

    private String incVersionCacheAndGet(String formId) {
        String key = formId + "_version";
        String version = null;
        String versionCache = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(versionCache)) {
            stringRedisTemplate.opsForValue().set(key, "1");
            version = "1.0";
        } else {
            if (StringUtils.isNumeric(versionCache)) {
                Integer n = Integer.valueOf(versionCache).intValue() + 1;
                stringRedisTemplate.opsForValue().set(key, n.toString());
                version = n + ".0";
            }
        }
        return version;
    }

    @Override
    public BusinessResult<Boolean> publish(FormDefinitionPublishParam param) {
        FormDefinitionConditionParam build = FormDefinitionConditionParam.builder()
                .id(param.getId())
                .build();
        QueryWrapper<FormDefinitionEntity> queryWrapper = queryWrapper(build);
        List<FormDefinitionEntity> list = list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            FormDefinitionEntity formDefinitionEntity = list.get(0);
            formDefinitionEntity.setFormStatus(FormStatusEnum.P.getCode());
            saveOrUpdate(formDefinitionEntity);
        }
        return BusinessResult.success(true);
    }

    private List<FormElementEntity> transfer(String defId, List<FormElementVo> vos) {
        List<FormElementEntity> results = null;
        if (StringUtils.isNotBlank(defId) && CollectionUtils.isNotEmpty(vos)) {
            if (isExistEleFlag(vos)) {
                throw new AppException("50009381");
            }

            results = vos.parallelStream()
                    .filter(Objects::nonNull)
                    .map(vo -> {
                        FormElementEntity entity = new FormElementEntity();
                        entity.setId(vo.getId());
                        entity.setFormId(defId);
                        entity.setEleFlag(vo.getModel());
                        entity.setEleLabel(vo.getDesc());
                        entity.setEleJson(vo.getEleJson());
                        return entity;
                    }).collect(Collectors.toList());
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    private boolean isExistEleFlag(List<FormElementVo> vos) {
        boolean flag = false;
        List<String> models = vos.parallelStream()
                .filter(Objects::nonNull)
                .map(FormElementVo::getModel)
                .collect(Collectors.toList());
        FormElementConditionParam build = FormElementConditionParam.builder()
                .models(models)
                .build();
        List<FormElementEntity> elementList = formElementService.getElementList(build);
        if (CollectionUtils.isNotEmpty(elementList)) {
            flag = true;
        }
        return flag;
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<FormDefinitionResult> formDetail(FormDetailQueryParam param) {
        String id = param.getId();
        FormDefinitionResult result = new FormDefinitionResult();
        FormModelVo vo = new FormModelVo();
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> id, executor);
        CompletableFuture<Void> formFuture = supplyAsync.thenAcceptAsync(x -> {
            FormDefinitionEntity byId = getById(x);
            result.setId(byId.getId());
            result.setFormName(byId.getFormName());
            result.setFormDesc(byId.getFormDesc());
            result.setFormStatus(byId.getFormStatus());
            result.setUserId(byId.getUserId());
            result.setModelEditorJson(vo);
            vo.setConfig(byId.getDefJson());
        }, executor);
        CompletableFuture<Void> eleFuture = supplyAsync.thenAcceptAsync(x -> {
            FormElementConditionParam build = FormElementConditionParam.builder()
                    .formId(x)
                    .build();
            List<FormElementEntity> elementList = formElementService.getElementList(build);
            vo.setList(transferEleVo(elementList));
        }, executor);
        CompletableFuture<Void> permFuture = supplyAsync.thenAcceptAsync(x -> {
            FormPermEntityConditionParam build = FormPermEntityConditionParam.builder()
                    .formId(x)
                    .build();
            List<FormPermEntity> permList = formPermService.getPermList(build);
            vo.setAuthList(transferPermVo(permList));
        }, executor);
        CompletableFuture.allOf(formFuture, permFuture, eleFuture).join();
        return BusinessResult.success(result);
    }

    private List<FormElementVo> transferEleVo(List<FormElementEntity> elementList) {
        List<FormElementVo> results = null;
        if (CollectionUtils.isNotEmpty(elementList)) {
            results = elementList.parallelStream()
                    .filter(Objects::nonNull)
                    .map(ele -> {
                        FormElementVo vo = new FormElementVo();
                        BeanCopyUtils.copyProperties(ele, vo);
                        vo.setModel(ele.getEleFlag());
                        return vo;
                    }).collect(Collectors.toList());
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    private List<ElementPermVo> transferPermVo(List<FormPermEntity> permList) {
        List<ElementPermVo> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(permList)) {
            Map<String, List<FormPermEntity>> elePermGroup = permList.parallelStream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(FormPermEntity::getEleId));

            elePermGroup.forEach((k, v) -> {
                ElementPermVo vo = new ElementPermVo();
                UserElePerm perms = new UserElePerm();
                vo.setModel(k);
                vo.setPermission(perms);
                Map<String, List<FormPermEntity>> permGroup = v.parallelStream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.groupingBy(FormPermEntity::getPermFlag));
                permGroup.forEach((p, l) -> {
                    if (PermFlagEnum.CAN_EDIT.getCode().equals(p)) {
                        FormPermEntity permEntity = l.get(0);
                        String userIds = permEntity.getUserIds();
                        String[] split = StringUtils.split(userIds, ",");
                        perms.setCanEdit(Arrays.asList(split));
                    }
                    if (PermFlagEnum.CAN_HIDDEN.getCode().equals(p)) {
                        FormPermEntity permEntity = l.get(0);
                        String userIds = permEntity.getUserIds();
                        String[] split = StringUtils.split(userIds, ",");
                        perms.setCanHidden(Arrays.asList(split));
                    }
                });
                results.add(vo);
            });
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    private List<FormPermEntity> transfer(List<ElementPermVo> vos, String defId) {
        List<FormPermEntity> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(vos) && StringUtils.isNotBlank(defId)) {
            vos.stream()
                    .filter(Objects::nonNull)
                    .forEach(vo -> {
                        String eleId = vo.getModel();
                        UserElePerm permission = vo.getPermission();

                        List<String> canEdit = permission.getCanEdit();
                        if (CollectionUtils.isNotEmpty(canEdit)) {
                            results.add(transfer(PermFlagEnum.CAN_EDIT.getCode(), defId, eleId, canEdit));
                        }
                        List<String> canHidden = permission.getCanHidden();
                        if (CollectionUtils.isNotEmpty(canHidden)) {
                            results.add(transfer(PermFlagEnum.CAN_HIDDEN.getCode(), defId, eleId, canHidden));
                        }
                    });
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    @Override
    public BusinessResult<Boolean> disable(FormDisableParam param) {
        String id = param.getId();
        FormDefinitionEntity byId = getById(id);
        if (Objects.nonNull(byId)) {
            String formStatus = byId.getFormStatus();
            if (FormStatusEnum.P.getCode().equals(formStatus)) {
                byId.setFormStatus(FormStatusEnum.T.getCode());
                updateById(byId);
            } else {
                throw new AppException("50009380");
            }
        }
        return BusinessResult.success(true);
    }

    private FormPermEntity transfer(String permFlag, String formId, String eleId, List<String> userIds) {
        FormPermEntity entity = new FormPermEntity();
        entity.setFormId(formId);
        entity.setEleId(eleId);
        String canEditStr = String.join(",", userIds);
        entity.setPermFlag(permFlag);
        entity.setUserIds(canEditStr);
        return entity;
    }

    private QueryWrapper<FormDefinitionEntity> queryWrapper(FormDefinitionConditionParam param) {
        QueryWrapper<FormDefinitionEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getFormName())) {
            wrapper.like(FormDefinitionEntity.FORM_NAME, param.getFormName());
        }
        if (Objects.nonNull(param.getFormStatus())) {
            wrapper.eq(FormDefinitionEntity.FORM_STATUS, param.getFormStatus().getCode());
        }
        if (StringUtils.isNotBlank(param.getFormVersion())) {
            wrapper.eq(FormDefinitionEntity.FORM_VERSION, param.getFormVersion());
        }
        String[] status = {FormStatusEnum.P.getCode(), FormStatusEnum.D.getCode(), FormStatusEnum.T.getCode()};
        wrapper.in(FormDefinitionEntity.FORM_STATUS, status);
        wrapper.orderByDesc(FormDefinitionEntity.CREATE_TIME);
        return wrapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BusinessResult<Boolean> deleteFormByIds(FormDefinitionEntityDelParam param) {
        List<String> ids = param.getIds();
        if (CollectionUtils.isEmpty(ids)) {
            throw new AppException("50000021");
        }
        formDefinitionMapper.updateStatusByIds(ids, FormStatusEnum.C.getCode());
        formHiDefinitionService.updateStatusByFormDefiIds(ids);
        return BusinessResult.success(true);
    }

}
