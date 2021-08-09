package com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.Query;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import com.jinninghui.icreditdatasphere.icreditstudio.common.enums.DeleteFlagEnum;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.entity.InfoReceiverEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.entity.InformationEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.enums.BroadCastEnum;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.enums.InfoTypeEnum;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.enums.ReadStatusEnum;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.mapper.InformationMapper;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.service.InfoReceiverService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.service.InformationService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.service.param.*;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.information.service.result.*;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.entity.UserEntity;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.UserService;
import com.jinninghui.icreditdatasphere.icreditstudio.modules.system.user.service.param.UserEntityConditionParam;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service("informationService")
public class InformationServiceImpl extends ServiceImpl<InformationMapper, InformationEntity> implements InformationService {

    @Autowired
    private InfoReceiverService infoReceiverService;
    @Autowired
    private UserService userService;

    @Override
    @BusinessParamsValidate
    public BusinessResult<BusinessPageResult<InformationInBoxResult>> inBoxPage(InformationInBoxPageParam param) {
        List<InfoReceiverEntity> receivers = selectReceivers(InfoReceiverDataParam.builder()
                .receiverId(param.getUserId())
                .deleteFlag(DeleteFlagEnum.N)
                .build());
        List<InformationEntity> broadcastInfos = selectBroadcastInfos();
        Set<String> userInfoIds = findUserInfoIdsByReadStatus(param.getUserId(), ReadStatusEnum.ALL, receivers, broadcastInfos);
        IPage<InformationInBoxResult> resultPage = new Page<>();
        List<InformationInBoxResult> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(userInfoIds)) {
            QueryWrapper<InformationEntity> wrapper = queryWrapper(InformationDataParam.builder()
                    .ids(userInfoIds)
                    .infoTitle(param.getInfoTitle())
                    .infoType(InfoTypeEnum.find(param.getInfoType()))
//                    .startSendTime(String.valueOf(Objects.nonNull(param.getStartTime()) ? param.getStartTime() : ""))
                    .startSendTime(param.getStartTime())
//                    .endSendTime(String.valueOf(Objects.nonNull(param.getStartTime()) ? param.getStartTime() : ""))
                    .endSendTime(param.getEndTime())
                    .build());
            IPage<InformationEntity> page = page(new Query<InformationEntity>().getPage(param), wrapper);
            BeanCopyUtils.copyProperties(page, resultPage);
            List<InformationEntity> records = page.getRecords();
            if (CollectionUtils.isNotEmpty(records)) {
                Set<String> readInfoIds = findUserInfoIdsByReceivers(param.getUserId(), ReadStatusEnum.Y, receivers);
                Set<String> unReadInfoIds = findUserUnreadReceiveInfoIds(param.getUserId(), receivers, broadcastInfos);
                results = transferToInboxResult(readInfoIds, unReadInfoIds, records, receivers);
                resultPage.setRecords(results);
            }
        }
        return BusinessResult.success(BusinessPageResult.build(resultPage, param));
    }

    @SuppressWarnings("all")
    private List<InformationInBoxResult> transferToInboxResult(Set<String> readInfoIds, Set<String> unReadInfoIds, List<InformationEntity> records, List<InfoReceiverEntity> receivers) {
        Set<String> senderIds = findInfoSenderField(records);
        List<UserEntity> users = getUsers(UserEntityConditionParam.builder().ids(senderIds).build());
        List<InformationInBoxResult> results = records.parallelStream().filter(Objects::nonNull)
                .map(info -> {
                    InformationInBoxResult result = new InformationInBoxResult();
                    BeanCopyUtils.copyProperties(info, result);
                    List<String> senderNames = findUserNameByIds(Sets.newHashSet(info.getSenderId()), users);
                    if (CollectionUtils.isNotEmpty(senderNames)) {
                        result.setSenderName(senderNames.get(0));
                    }

                    if (Objects.nonNull(readInfoIds) && readInfoIds.contains(info.getId())) {
                        Optional.ofNullable(receivers).orElse(Lists.newArrayList())
                                .parallelStream()
                                .filter(rec -> info.getId().equals(rec.getInfoId()))
                                .forEach(rec -> result.setReadTime(rec.getReadTime()));
                        result.setReadStatus(ReadStatusEnum.Y.getCode());
                    }
                    if (Objects.nonNull(unReadInfoIds) && unReadInfoIds.contains(info.getId())) {
                        result.setReadStatus(ReadStatusEnum.N.getCode());
                    }
                    return result;
                }).collect(Collectors.toList());
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    @SuppressWarnings("all")
    @Override
    public BusinessResult<BusinessPageResult<InformationResult>> outBoxPage(InformationOutBoxPageParam param) {
        String receiverName = param.getReceiverName();
        Set<String> nameConditionInfoIds = managerNameCondition(receiverName, null);
        IPage<InformationResult> resultPage = new Page<>();
        if (!(StringUtils.isNotBlank(receiverName) && CollectionUtils.isEmpty(nameConditionInfoIds))) {
            QueryWrapper<InformationEntity> wrapper = queryWrapper(InformationDataParam.builder()
                    .ids(nameConditionInfoIds)
                    .senderId(param.getUserId())
                    .infoTitle(param.getInfoTitle())
                    .infoType(InfoTypeEnum.find(param.getInfoType()))
//                    .startSendTime(String.valueOf(Objects.nonNull(param.getStartTime()) ? param.getStartTime() : ""))
                    .startSendTime(param.getStartTime())
//                    .endSendTime(String.valueOf(Objects.nonNull(param.getEndTime()) ? param.getStartTime() : ""))
                    .endSendTime(param.getEndTime())
                    .deleteFlag(DeleteFlagEnum.N)
                    .build());
            IPage<InformationEntity> page = page(new Query<InformationEntity>().getPage(param), wrapper);
            List<InformationEntity> records = page.getRecords();
            List<InformationResult> results = transferToInformationResult(records);
            BeanCopyUtils.copyProperties(page, resultPage);
            resultPage.setRecords(results);
        }
        return BusinessResult.success(BusinessPageResult.build(resultPage, param));
    }

    private List<InformationResult> transferToInformationResult(List<InformationEntity> records) {
        List<InformationResult> results = null;
        if (CollectionUtils.isNotEmpty(records)) {
            Set<String> senderIds = findInfoSenderField(records);
            Set<String> infoIds = findInfoIds(records);
            List<InfoReceiverEntity> infoReceiverEntities = selectReceivers(InfoReceiverDataParam.builder().infoIds(infoIds).build());
            Set<String> receiverIds = findInfoReceiveField(infoReceiverEntities);
            Set<String> userIds = Sets.newHashSet(senderIds);
            userIds.addAll(receiverIds);
            List<UserEntity> users = getUsers(UserEntityConditionParam.builder().ids(userIds).build());
            results = records.stream().filter(Objects::nonNull)
                    .map(info -> {
                        InformationResult result = new InformationResult();
                        BeanCopyUtils.copyProperties(info, result);
                        String senderId = result.getSenderId();
                        List<String> senderNames = findUserNameByIds(Sets.newHashSet(senderId), users);
                        if (CollectionUtils.isNotEmpty(senderNames)) {
                            result.setSenderName(senderNames.get(0));
                        }
                        List<InfoReceiverEntity> receivesByInfoId = findReceivesByInfoId(info.getId(), infoReceiverEntities);
                        if (CollectionUtils.isNotEmpty(receivesByInfoId) && !BroadCastEnum.Y.getCode().equals(info.getBroadcastFlag())) {
//                            InfoReceiverEntity infoReceiverEntity = receivesByInfoId.get(0);
                            result.setReceiverIds(findReceiverUserIds(receivesByInfoId));
                            List<String> receiveNames = findUserNameByIds(result.getReceiverIds(), users);
                            result.setReceiverNames(receiveNames.parallelStream().collect(Collectors.toSet()));
                        }
                        return result;
                    }).collect(Collectors.toList());
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    private Set<String> findReceiverUserIds(List<InfoReceiverEntity> receivers) {
        Set<String> results = null;
        if (CollectionUtils.isNotEmpty(receivers)) {
            results = receivers.parallelStream()
                    .filter(Objects::nonNull)
                    .map(InfoReceiverEntity::getReceiverId)
                    .collect(Collectors.toSet());
        }
        return Optional.ofNullable(results).orElse(Sets.newHashSet());
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> managerDelInfo(InformationManagerDelInfoParam param) {
        Set<String> ids = param.getIds();
        Set<String> delIds = removeEmpty(ids);
        if (CollectionUtils.isNotEmpty(delIds)) {
            List<InfoReceiverEntity> infoReceivers = selectReceivers(InfoReceiverDataParam.builder().infoIds(ids).build());
            Set<String> receiverIds = findReceiverIds(infoReceivers);
            removeByIds(ids);
            Set<String> delReceiverIds = removeEmpty(receiverIds);
            if (CollectionUtils.isNotEmpty(delReceiverIds)) {
                infoReceiverService.removeByIds(receiverIds);
            }
        }
        return BusinessResult.success(true);
    }

    private Set<String> removeEmpty(Set<String> strs) {
        return Optional.ofNullable(strs).orElse(Sets.newHashSet())
                .parallelStream()
                .filter(StringUtils::isNoneBlank)
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("all")
    @Override
    public BusinessResult<BusinessPageResult<InformationResult>> infoManagerPage(InformationManagerPageParam param) {
        IPage<InformationResult> resultPage = new Page<>();
        String receiverName = param.getReceiverName();
        Set<String> nameConditionInfoIds = managerNameCondition(receiverName, null);
        if (!(StringUtils.isNotBlank(receiverName) && CollectionUtils.isEmpty(nameConditionInfoIds))) {
            QueryWrapper<InformationEntity> wrapper = queryWrapper(InformationDataParam.builder()
                    .ids(nameConditionInfoIds)
                    .infoTitle(param.getInfoTitle())
                    .infoType(InfoTypeEnum.find(param.getInfoType()))
                    .startSendTime(param.getStartTime())
                    .endSendTime(param.getEndTime())
                    .build());

            IPage<InformationEntity> page = page(new Query<InformationEntity>().getPage(param), wrapper);
            List<InformationEntity> records = page.getRecords();
            List<InformationResult> results = transferToInformationResult(records);
            BeanCopyUtils.copyProperties(page, resultPage);
            resultPage.setRecords(results);
        }
        return BusinessResult.success(BusinessPageResult.build(resultPage, param));
    }

    private Set<String> managerNameCondition(String name, DeleteFlagEnum deleteFlag) {
        Set<String> nameConditionInfoIds = Sets.newHashSet();
        if (StringUtils.isNotBlank(name)) {
            List<UserEntity> users = getUsers(UserEntityConditionParam.builder().name(name).build());
            Set<String> userIds = users.parallelStream().filter(Objects::nonNull)
                    .map(UserEntity::getId)
                    .collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(userIds)) {
                List<InfoReceiverEntity> infoReceiverEntities = selectReceivers(InfoReceiverDataParam.builder()
                        .receiverIds(userIds)
                        .deleteFlag(deleteFlag)
                        .build());
                if (CollectionUtils.isNotEmpty(infoReceiverEntities)) {
                    nameConditionInfoIds.addAll(infoReceiverEntities.parallelStream().filter(Objects::nonNull)
                            .map(InfoReceiverEntity::getInfoId)
                            .collect(Collectors.toSet()));
                }
                List<InformationEntity> broadcastInfos = selectBroadcastInfos();
                //添加了未读广播消息
                nameConditionInfoIds.addAll(findUserUnreadBroadcastInfoIds(userIds, broadcastInfos));
            }
        }
        return Optional.ofNullable(nameConditionInfoIds).orElse(Sets.newHashSet());
    }

    private List<InfoReceiverEntity> findReceivesByInfoId(String infoId, List<InfoReceiverEntity> receivers) {
        List<InfoReceiverEntity> results = null;
        if (StringUtils.isNotBlank(infoId) && CollectionUtils.isNotEmpty(receivers)) {
            results = receivers.parallelStream().filter(Objects::nonNull)
                    .filter(rec -> infoId.equals(rec.getInfoId()))
                    .collect(Collectors.toList());
        }
        return results;
    }

    private List<String> findUserNameByIds(Set<String> userIds, List<UserEntity> users) {
        List<String> names = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(users) && CollectionUtils.isNotEmpty(userIds)) {
            for (String userId : userIds) {
                Optional<String> first = users.parallelStream().filter(Objects::nonNull)
                        .filter(user -> userId.equals(user.getId()))
                        .map(UserEntity::getUserName)
                        .findFirst();
                if (first.isPresent()) {
                    names.add(first.get());
                }
            }
        }
        return names;
    }

    private Set<String> findInfoSenderField(List<InformationEntity> infos) {
        Set<String> results = null;
        if (CollectionUtils.isNotEmpty(infos)) {
            results = infos.parallelStream().filter(Objects::nonNull)
                    .map(InformationEntity::getSenderId)
                    .collect(Collectors.toSet());
        }
        return Optional.ofNullable(results).orElse(Sets.newHashSet());
    }

    private Set<String> findInfoReceiveField(List<InfoReceiverEntity> receivers) {
        Set<String> results = null;
        if (CollectionUtils.isNotEmpty(receivers)) {
            results = receivers.parallelStream().filter(Objects::nonNull)
                    .map(InfoReceiverEntity::getReceiverId)
                    .collect(Collectors.toSet());
        }
        return Optional.ofNullable(results).orElse(Sets.newHashSet());
    }

    @SuppressWarnings("all")
    @Override
    @BusinessParamsValidate
    public BusinessResult<InfoNoticeCountResult> infoCount(InformationCountParam param) {
        List<InfoReceiverEntity> userReceivers = selectReceivers(InfoReceiverDataParam.builder()
                .receiverId(param.getUserId())
                .deleteFlag(DeleteFlagEnum.N)
                .build());
        List<InformationEntity> broadcastInfos = selectBroadcastInfos();
        //用户已读消息
        Set<String> userReadReceiveInfoIds = findUserInfoIdsByReceivers(param.getUserId(), ReadStatusEnum.Y, userReceivers);
        Set<String> userUnreadReceiveInfoIds = findUserUnreadReceiveInfoIds(param.getUserId(), userReceivers, broadcastInfos);
        Set<String> userInfoIds = Sets.newHashSet(userReadReceiveInfoIds);
        userInfoIds.addAll(userUnreadReceiveInfoIds);

        InfoTypeEnum infoTypeEnum = InfoTypeEnum.find(param.getInfoType());
        QueryWrapper<InformationEntity> wrapper = queryWrapper(InformationDataParam.builder().infoType(infoTypeEnum).ids(userInfoIds).build());
        List<InformationEntity> list = list(wrapper);
        Set<String> infoIds = findInfoIds(list);
        //排除用户自己发的广播消息
        Set<String> excludeSelfSend = excludeSelfSendBroadcast(param.getUserId(), infoIds, broadcastInfos);
        Set<String> conditionUserReadInfoIds = userReadReceiveInfoIds.parallelStream().filter(StringUtils::isNoneBlank)
                .filter(infoId -> excludeSelfSend.contains(infoId))
                .collect(Collectors.toSet());
        Set<String> conditionUserUnReadInfoIds = userUnreadReceiveInfoIds.parallelStream().filter(StringUtils::isNoneBlank)
                .filter(infoId -> excludeSelfSend.contains(infoId))
                .collect(Collectors.toSet());
        InfoNoticeCountResult result = new InfoNoticeCountResult();
        result.setAllCount(conditionUserUnReadInfoIds.size() + conditionUserReadInfoIds.size());
        result.setUnReadCount(conditionUserUnReadInfoIds.size());
        result.setReadCount(conditionUserReadInfoIds.size());
        return BusinessResult.success(result);
    }

    @SuppressWarnings("all")
    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> userAllReadInfo(InformationAllReadParam param) {
        List<InfoReceiverEntity> userReceivers = selectReceivers(InfoReceiverDataParam.builder()
                .receiverId(param.getUserId())
                .deleteFlag(DeleteFlagEnum.N)
                .build());
        List<InformationEntity> broadcastInfos = selectBroadcastInfos();
        ReadStatusEnum readStatusEnum = ReadStatusEnum.find(param.getReadStatus());
        Set<String> userInfoIds = findUserInfoIdsByReadStatus(param.getUserId(), readStatusEnum, userReceivers, broadcastInfos);
        InfoTypeEnum infoTypeEnum = InfoTypeEnum.find(param.getInfoType());
        QueryWrapper<InformationEntity> wrapper = queryWrapper(InformationDataParam.builder().infoType(infoTypeEnum).ids(userInfoIds).build());
        List<InformationEntity> list = list(wrapper);
        Set<String> infoIds = findInfoIds(list);
        updateInfoReadStatus(param.getUserId(), infoIds, userReceivers);
        return BusinessResult.success(true);
    }

    private Set<String> findUserInfoIdsByReadStatus(String userId, ReadStatusEnum readStatus, List<InfoReceiverEntity> receivers, List<InformationEntity> broadcastInfos) {
        List<InfoReceiverEntity> userReceivers = receivers.parallelStream().filter(Objects::nonNull)
                .filter(rec -> userId.equals(rec.getReceiverId()))
                .collect(Collectors.toList());
        //用户已读消息
        Set<String> userReadReceiveInfoIds = findUserInfoIdsByReceivers(userId, ReadStatusEnum.Y, userReceivers);
        Set<String> userUnreadReceiveInfoIds = findUserUnreadReceiveInfoIds(userId, userReceivers, broadcastInfos);
        Set<String> userInfoIds = Sets.newHashSet();
        if (ReadStatusEnum.ALL.getCode().equals(readStatus.getCode())) {
            userInfoIds.addAll(findUserReceiveAllInfoIds(userId, userReceivers, broadcastInfos));
        } else if (ReadStatusEnum.N.getCode().equals(readStatus.getCode())) {
            userInfoIds.addAll(userUnreadReceiveInfoIds);
        } else {
            userInfoIds.addAll(userReadReceiveInfoIds);
        }
        userInfoIds = excludeSelfSendBroadcast(userId, userInfoIds, broadcastInfos);
        return userInfoIds;
    }

    private Set<String> excludeSelfSendBroadcast(String userId, Set<String> userInfoIds, List<InformationEntity> broadcastInfos) {
        Set<String> results = Sets.newHashSet();
        if (StringUtils.isNotBlank(userId) && CollectionUtils.isNotEmpty(userInfoIds)) {
            Set<String> broadcastInfoIds = Optional.ofNullable(broadcastInfos).orElse(Lists.newArrayList())
                    .parallelStream()
                    .filter(Objects::nonNull)
                    .filter(info -> userId.equals(info.getSenderId()))
                    .map(InformationEntity::getId)
                    .collect(Collectors.toSet());

            results.addAll(userInfoIds.parallelStream()
                    .filter(StringUtils::isNoneBlank)
                    .filter(id -> !broadcastInfoIds.contains(id))
                    .collect(Collectors.toSet()));
        }
        return results;
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<InfoNoticeResult> userInfo(InformationUserInfoParam param) {
        List<InformationEntity> infos = selectInfos(InformationDataParam.builder().id(param.getId()).build());
        List<InfoReceiverEntity> userReceivers = selectReceivers(InfoReceiverDataParam.builder()
                .receiverId(param.getUserId())
                .infoId(param.getId())
                .build());

        InfoNoticeResult result = null;
        if (CollectionUtils.isNotEmpty(infos)) {
            if (!(CollectionUtils.isNotEmpty(userReceivers) && DeleteFlagEnum.Y.getCode().equals(userReceivers.get(0).getDeleteFlag()))) {
                InformationEntity informationEntity = infos.get(0);
                updateInfoReadStatus(param.getUserId(), Sets.newHashSet(param.getId()), userReceivers);
                result = BeanCopyUtils.copyProperties(informationEntity, InfoNoticeResult.class);
                result.setReadStatus(ReadStatusEnum.Y.getCode());
                if (StringUtils.isNotBlank(result.getSenderId())) {
                    List<UserEntity> users = getUsers(UserEntityConditionParam.builder().ids(Sets.newHashSet(result.getSenderId())).build());
                    if (CollectionUtils.isNotEmpty(users)) {
                        UserEntity entity = users.get(0);
                        result.setSenderName(entity.getUserName());
                    }
                }
            }
        }
        return BusinessResult.success(result);
    }

    private void updateInfoReadStatus(String userId, Set<String> userReceiveInfoIds, List<InfoReceiverEntity> receivers) {
        if (CollectionUtils.isNotEmpty(userReceiveInfoIds) && StringUtils.isNotBlank(userId)) {
            Set<String> receiverIds = Optional.ofNullable(receivers).orElse(Lists.newArrayList())
                    .parallelStream()
                    .filter(Objects::nonNull)
                    .map(InfoReceiverEntity::getInfoId)
                    .collect(Collectors.toSet());
            Set<String> notExist = userReceiveInfoIds.parallelStream().filter(StringUtils::isNoneBlank)
                    .filter(infoId -> !receiverIds.contains(infoId))
                    .collect(Collectors.toSet());
            List<InfoReceiverEntity> transform = transform(notExist, Sets.newHashSet(userId), ReadStatusEnum.Y, DeleteFlagEnum.N);
            if (CollectionUtils.isNotEmpty(transform)) {
                List<InfoReceiverEntity> collect = transform.parallelStream().map(rec -> {
                    rec.setReadTime(String.valueOf(System.currentTimeMillis()));
                    return rec;
                }).collect(Collectors.toList());
                infoReceiverService.saveBatch(collect);
            }
            List<InfoReceiverEntity> updateReadStatusReceives = Optional.ofNullable(receivers).orElse(Lists.newArrayList())
                    .parallelStream()
                    .filter(Objects::nonNull)
                    .map(rec -> {
                        if (ReadStatusEnum.N.getCode().equals(rec.getReadStatus())) {
                            rec.setReadTime(String.valueOf(System.currentTimeMillis()));
                        }
                        rec.setReadStatus(ReadStatusEnum.Y.getCode());
                        return rec;
                    }).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(updateReadStatusReceives)) {
                infoReceiverService.updateBatchById(updateReadStatusReceives);
            }
        }
    }

    @SuppressWarnings("all")
    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> deleteAllUserInfos(InformationDelAllUserInfoParam param) {
        List<InfoReceiverEntity> userReceivers = selectReceivers(InfoReceiverDataParam.builder()
                .receiverId(param.getUserId())
                .deleteFlag(DeleteFlagEnum.N)
                .build());
        List<InformationEntity> broadcastInfos = selectBroadcastInfos();
        ReadStatusEnum readStatusEnum = ReadStatusEnum.find(param.getReadStatus());
        Set<String> userInfoIds = findUserInfoIdsByReadStatus(param.getUserId(), readStatusEnum, userReceivers, broadcastInfos);
        if (CollectionUtils.isNotEmpty(userInfoIds)) {
            InfoTypeEnum infoTypeEnum = InfoTypeEnum.find(param.getInfoType());
            QueryWrapper<InformationEntity> wrapper = queryWrapper(InformationDataParam.builder().infoType(infoTypeEnum).ids(userInfoIds).build());
            List<InformationEntity> conditionInfos = list(wrapper);
            Set<String> infoIds = findInfoIds(conditionInfos);

            if (CollectionUtils.isNotEmpty(userReceivers) && CollectionUtils.isNotEmpty(infoIds)) {
                List<InfoReceiverEntity> updateReceiver = userReceivers.parallelStream().filter(Objects::nonNull)
                        .filter(rec -> infoIds.contains(rec.getInfoId()))
                        .collect(Collectors.toList());
                List<InfoReceiverEntity> infoReceiverEntities = updateDelFlag(DeleteFlagEnum.Y, updateReceiver);
                if (CollectionUtils.isNotEmpty(infoReceiverEntities)) {
                    infoReceiverService.updateBatchById(infoReceiverEntities);
                }
            }
            Set<String> userUnreadBroadcastInfoIds = findUserUnreadBroadcastInfoIds(Sets.newHashSet(param.getUserId()), broadcastInfos);
            if (CollectionUtils.isNotEmpty(userUnreadBroadcastInfoIds)) {
                Set<String> saveInfoIds = userUnreadBroadcastInfoIds.parallelStream().filter(StringUtils::isNoneBlank)
                        .filter(infoId -> infoIds.contains(infoId))
                        .collect(Collectors.toSet());
                List<InfoReceiverEntity> transform = transform(saveInfoIds, Sets.newHashSet(param.getUserId()), ReadStatusEnum.Y, DeleteFlagEnum.Y);
                infoReceiverService.saveBatch(transform);
            }
        }
        return BusinessResult.success(true);
    }

    private Set<String> findReceiverIds(List<InfoReceiverEntity> receivers) {
        Set<String> results = null;
        if (CollectionUtils.isNotEmpty(receivers)) {
            results = receivers.parallelStream().filter(Objects::nonNull)
                    .map(InfoReceiverEntity::getId)
                    .collect(Collectors.toSet());
        }
        return results;
    }

    @Override
    public BusinessResult<Boolean> deleteOutBoxInfo(InformationDelUserInfoParam param) {
        Set<String> ids = param.getIds();
        Set<String> removeEmptyIds = removeEmpty(ids);
        if (CollectionUtils.isNotEmpty(removeEmptyIds)) {
            List<InformationEntity> collect = removeEmptyIds.parallelStream()
                    .map(id -> {
                        InformationEntity entity = new InformationEntity();
                        entity.setId(id);
                        entity.setDeleteFlag(DeleteFlagEnum.Y.getCode());
                        return entity;
                    }).collect(Collectors.toList());
            updateBatchById(collect);
        }
        return BusinessResult.success(true);
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> deleteUserInfo(InformationDelUserInfoParam param) {
        Set<String> ids = param.getIds();
        List<InfoReceiverEntity> userReceivers = selectReceivers(InfoReceiverDataParam.builder().receiverId(param.getUserId()).build());
        List<InformationEntity> broadcastInfos = selectBroadcastInfos();
        Set<String> userUnreadBroadcastInfoIds = findUserUnreadBroadcastInfoIds(Sets.newHashSet(param.getUserId()), broadcastInfos);
        Set<String> isUnreadBroadcastIds = ids.parallelStream().filter(Objects::nonNull)
                .filter(id -> userUnreadBroadcastInfoIds.contains(id))
                .collect(Collectors.toSet());
        List<InfoReceiverEntity> transform = transform(isUnreadBroadcastIds, Sets.newHashSet(param.getUserId()), ReadStatusEnum.Y, DeleteFlagEnum.Y);
        if (CollectionUtils.isNotEmpty(transform)) {
            infoReceiverService.saveBatch(transform);
        }
//        List<InfoReceiverEntity> updateDelFlagReceivers = selectReceivers(InfoReceiverDataParam.builder().receiverId(param.getUserId()).infoIds(ids).build());
        List<InfoReceiverEntity> updateDelFlagReceivers = userReceivers.parallelStream().filter(Objects::nonNull)
                .filter(rec -> ids.contains(rec.getInfoId()))
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(updateDelFlagReceivers)) {
            List<InfoReceiverEntity> infoReceiverEntities = updateDelFlag(DeleteFlagEnum.Y, updateDelFlagReceivers);
            infoReceiverService.updateBatchById(infoReceiverEntities);
        }
        return BusinessResult.success(true);
    }

    private List<InfoReceiverEntity> updateDelFlag(DeleteFlagEnum deleteFlag, List<InfoReceiverEntity> receivers) {
        List<InfoReceiverEntity> results = null;
        if (CollectionUtils.isNotEmpty(receivers)) {
            results = receivers.parallelStream()
                    .filter(Objects::nonNull)
                    .map(rec -> {
                        rec.setDeleteFlag(deleteFlag.getCode());
                        return rec;
                    }).collect(Collectors.toList());
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    @SuppressWarnings("all")
    @Override
    @BusinessParamsValidate
    public BusinessResult<BusinessPageResult<InfoNoticeResult>> infoNoticePage(InformationNoticePageParam param) {
        List<InfoReceiverEntity> userReceivers = selectReceivers(InfoReceiverDataParam.builder()
                .receiverId(param.getUserId())
                .deleteFlag(DeleteFlagEnum.N)
                .build());
        List<InformationEntity> broadcastInfos = selectBroadcastInfos();
        //用户已读消息
        Set<String> userReadReceiveInfoIds = findUserInfoIdsByReceivers(param.getUserId(), ReadStatusEnum.Y, userReceivers);
        Set<String> userUnreadReceiveInfoIds = findUserUnreadReceiveInfoIds(param.getUserId(), userReceivers, broadcastInfos);
        ReadStatusEnum readStatusEnum = ReadStatusEnum.find(param.getReadStatus());
        Set<String> userInfoIds = findUserInfoIdsByReadStatus(param.getUserId(), readStatusEnum, userReceivers, broadcastInfos);

        List<InformationEntity> records = Lists.newArrayList();
        IPage<InformationEntity> page = new Page<>();
        if (CollectionUtils.isNotEmpty(userInfoIds)) {
            InfoTypeEnum infoTypeEnum = InfoTypeEnum.find(param.getInfoType());
            QueryWrapper<InformationEntity> wrapper = queryWrapper(InformationDataParam.builder()
                    .infoType(infoTypeEnum)
                    .infoTitle(param.getInfoTitle())
//                    .deleteFlag(DeleteFlagEnum.N)
                    .ids(userInfoIds)
                    .build());
            page = page(new Query<InformationEntity>().getPage(param), wrapper);
            records = page.getRecords();
        }
        Page<InfoNoticeResult> resultPage = new Page<>();
        BeanCopyUtils.copyProperties(page, resultPage);
        if (CollectionUtils.isNotEmpty(records)) {
            List<InfoNoticeResult> infoNoticeResults = transferToInfoNoticeResult(userReadReceiveInfoIds, userUnreadReceiveInfoIds, records);
            resultPage.setRecords(infoNoticeResults);
        }
        return BusinessResult.success(BusinessPageResult.build(resultPage, param));
    }

    private List<InfoNoticeResult> transferToInfoNoticeResult(Set<String> readInfoIds, Set<String> unReadInfoIds, List<InformationEntity> infos) {
        List<InfoNoticeResult> results = null;
        if (CollectionUtils.isNotEmpty(infos)) {
            results = infos.parallelStream().filter(Objects::nonNull)
                    .map(info -> {
                        InfoNoticeResult result = new InfoNoticeResult();
                        BeanCopyUtils.copyProperties(info, result);
                        if (Objects.nonNull(readInfoIds) && readInfoIds.contains(info.getId())) {
                            result.setReadStatus(ReadStatusEnum.Y.getCode());
                        }
                        if (Objects.nonNull(unReadInfoIds) && unReadInfoIds.contains(info.getId())) {
                            result.setReadStatus(ReadStatusEnum.N.getCode());
                        }
                        return result;
                    }).collect(Collectors.toList());
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    private Set<String> findUserUnreadReceiveInfoIds(String userId, List<InfoReceiverEntity> receivers, List<InformationEntity> broadcastInfos) {
        Set<String> results = Sets.newHashSet();
        results.addAll(findUserUnreadOrdinaryInfoIds(userId, receivers));
        results.addAll(findUserUnreadBroadcastInfoIds(Sets.newHashSet(userId), broadcastInfos));
        return results;
    }


    private Set<String> findUserInfoIdsByReceivers(String userId, ReadStatusEnum readStatus, List<InfoReceiverEntity> receivers) {
        Set<String> results = null;
        if (StringUtils.isNotBlank(userId) && CollectionUtils.isNotEmpty(receivers)) {
            results = receivers.parallelStream().filter(Objects::nonNull)
                    .filter(receiver -> {
                        boolean flag1 = userId.equals(receiver.getReceiverId());
                        boolean flag2 = true;
                        if (Objects.nonNull(readStatus) && !ReadStatusEnum.ALL.equals(readStatus)) {
                            flag2 = readStatus.getCode().equals(receiver.getReadStatus());
                        }
                        return flag1 && flag2;
                    }).map(InfoReceiverEntity::getInfoId)
                    .collect(Collectors.toSet());

        }
        return Optional.ofNullable(results).orElse(Sets.newHashSet());
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> sendInfo(InformationSendParam param) {
        InformationEntity entity = new InformationEntity();
        BeanCopyUtils.copyProperties(param, entity);
//        entity.setSendTime(String.valueOf(System.currentTimeMillis()));
        entity.setSendTime(System.currentTimeMillis());
        entity.setBroadcastFlag(BroadCastEnum.N.getCode());
        entity.setDeleteFlag(DeleteFlagEnum.N.getCode());
        Set<String> receiverIds = param.getReceiverIds();
        if (CollectionUtils.isEmpty(receiverIds)) {
            entity.setBroadcastFlag(BroadCastEnum.Y.getCode());
        }
        save(entity);
        if (CollectionUtils.isNotEmpty(receiverIds)) {
            List<InfoReceiverEntity> transform = transform(Sets.newHashSet(entity.getId()), receiverIds, ReadStatusEnum.N, DeleteFlagEnum.N);
            infoReceiverService.saveBatch(transform);
        }
        return BusinessResult.success(true);
    }

    private List<InfoReceiverEntity> transform(Set<String> infoIds, Set<String> receiverUserIds, ReadStatusEnum readStatus, DeleteFlagEnum deleteFlag) {
        List<InfoReceiverEntity> results = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(infoIds) && CollectionUtils.isNotEmpty(receiverUserIds)) {
            for (String infoId : infoIds) {
                results.addAll(receiverUserIds.parallelStream()
                        .filter(StringUtils::isNotBlank)
                        .map(receiverId -> {
                            InfoReceiverEntity entity = new InfoReceiverEntity();
                            entity.setInfoId(infoId);
                            entity.setReceiverId(receiverId);
                            entity.setReadStatus(readStatus.getCode());
                            entity.setDeleteFlag(deleteFlag.getCode());
                            return entity;
                        }).collect(Collectors.toList()));
            }
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    @Override
    @BusinessParamsValidate
    public BusinessResult<InfoCountResult> pollingUnreadInfos(InformationUnreadParam param) {
        //用户做为接收者信息
        List<InfoReceiverEntity> receivers = selectReceivers(InfoReceiverDataParam.builder()
                .receiverId(param.getUserId()).deleteFlag(DeleteFlagEnum.N).build());
        //广播类型信息
        List<InformationEntity> broadcastInfos = selectBroadcastInfos();
        //用户未读广播消息
        Set<String> unreadBroadcastInfoIds = findUserUnreadBroadcastInfoIds(Sets.newHashSet(param.getUserId()), broadcastInfos);
        unreadBroadcastInfoIds = excludeSelfSendBroadcast(param.getUserId(), unreadBroadcastInfoIds, broadcastInfos);
        List<InformationEntity> unreadBroadcastInfos = findInformationByIds(unreadBroadcastInfoIds, broadcastInfos);

        List<InformationEntity> sysInfos = findClassifications(InfoTypeEnum.SYSTEM, unreadBroadcastInfos);
        List<InformationEntity> noticeInfos = findClassifications(InfoTypeEnum.NOTICE, unreadBroadcastInfos);
        List<InformationEntity> warningInfos = findClassifications(InfoTypeEnum.WARNING, unreadBroadcastInfos);
        //用户未读普通消息
        Set<String> unreadOrdinaryInfoIds = findUserUnreadOrdinaryInfoIds(param.getUserId(), receivers);
        if (CollectionUtils.isNotEmpty(unreadOrdinaryInfoIds)) {
            List<InformationEntity> unreadOrdinaryInfos = selectInfos(InformationDataParam.builder()
                    .ids(unreadOrdinaryInfoIds).deleteFlag(DeleteFlagEnum.N).build());
            sysInfos.addAll(findClassifications(InfoTypeEnum.SYSTEM, unreadOrdinaryInfos));
            noticeInfos.addAll(findClassifications(InfoTypeEnum.NOTICE, unreadOrdinaryInfos));
            warningInfos.addAll(findClassifications(InfoTypeEnum.WARNING, unreadOrdinaryInfos));
        }
        InfoCountResult result = new InfoCountResult();
        result.setNUnreadCount(noticeInfos.size());
        result.setSUnreadCount(sysInfos.size());
        result.setWUnreadCount(warningInfos.size());
        result.setTotalUnreadCount(noticeInfos.size() + sysInfos.size() + warningInfos.size());
        return BusinessResult.success(result);
    }

    private List<InformationEntity> findInformationByIds(Set<String> ids, List<InformationEntity> infos) {
        List<InformationEntity> results = null;
        if (CollectionUtils.isNotEmpty(ids)) {
            results = Optional.ofNullable(infos).orElse(Lists.newArrayList())
                    .parallelStream()
                    .filter(Objects::nonNull)
                    .filter(info -> ids.contains(info.getId()))
                    .collect(Collectors.toList());
        } /*else {
            results = infos;
        }*/
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    private List<InformationEntity> findClassifications(InfoTypeEnum type, List<InformationEntity> infos) {
        List<InformationEntity> results = null;
        if (Objects.nonNull(type) && CollectionUtils.isNotEmpty(infos)) {
            results = infos.parallelStream().filter(Objects::nonNull)
                    .filter(info -> type.getCode().equals(info.getInfoType()))
                    .collect(Collectors.toList());
        }
        return Optional.ofNullable(results).orElse(Lists.newArrayList());
    }

    private Set<String> findUserUnreadOrdinaryInfoIds(String userId, List<InfoReceiverEntity> receivers) {
        Set<String> results = null;
        if (StringUtils.isNotBlank(userId) && CollectionUtils.isNotEmpty(receivers)) {
            results = receivers.parallelStream().filter(Objects::nonNull)
                    .filter(receiver -> userId.equals(receiver.getReceiverId()) && ReadStatusEnum.N.getCode().equals(receiver.getReadStatus()))
                    .map(InfoReceiverEntity::getInfoId)
                    .collect(Collectors.toSet());
        }
        return Optional.ofNullable(results).orElse(Sets.newHashSet());
    }

    private Set<String> findUserReceiveAllInfoIds(String userId, List<InfoReceiverEntity> receivers, List<InformationEntity> infos) {
        Set<String> userReceiverIds = Optional.ofNullable(receivers).orElse(Lists.newArrayList())
                .parallelStream()
                .filter(Objects::nonNull)
                .filter(rec -> userId.equals(rec.getReceiverId()))
                .map(InfoReceiverEntity::getInfoId)
                .collect(Collectors.toSet());
        List<InformationEntity> collect = Optional.ofNullable(infos).orElse(Lists.newArrayList())
                .parallelStream()
                .filter(info -> BroadCastEnum.Y.getCode().equals(info.getBroadcastFlag()))
                .collect(Collectors.toList());
        Set<String> userUnreadBroadcastInfoIds = findUserUnreadBroadcastInfoIds(Sets.newHashSet(userId), collect);
        Set<String> results = Sets.newHashSet(userReceiverIds);
        results.addAll(userUnreadBroadcastInfoIds);
        return results;
    }

    private Set<String> findReceiveOrdinaryInfoIds(List<InformationEntity> infos) {
        Set<String> toOrdinaryInfoIds = Sets.newHashSet();
        findSeparationOrdinaryAndBroadcast(infos, toOrdinaryInfoIds, Sets.newHashSet());
        Set<String> results = toOrdinaryInfoIds;
        return Optional.ofNullable(results).orElse(Sets.newHashSet());
    }

    private void findSeparationOrdinaryAndBroadcast(List<InformationEntity> infos, final Set<String> toOrdinaryInfoIds, final Set<String> toBroadcastInfoIds) {
        if (CollectionUtils.isNotEmpty(infos)) {
            infos.parallelStream()
                    .filter(Objects::nonNull)
                    .forEach(info -> {
                        if (BroadCastEnum.Y.getCode().equals(info.getBroadcastFlag())) {
                            toBroadcastInfoIds.add(info.getId());
                        } else {
                            toOrdinaryInfoIds.add(info.getId());
                        }
                    });
        }
    }

    private Set<String> findUserUnreadBroadcastInfoIds(Set<String> userIds, List<InformationEntity> broadcastInfos) {
        Set<String> results = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(userIds)) {
            for (String userId : userIds) {
                Set<String> broadcastInfoIds = findBroadcastInfoIds(broadcastInfos);
                List<InfoReceiverEntity> userBroadcastReceivers = selectReceivers(InfoReceiverDataParam.builder().receiverId(userId).infoIds(broadcastInfoIds).build());
                Set<String> collect = userBroadcastReceivers.parallelStream().filter(Objects::nonNull)
                        .map(InfoReceiverEntity::getInfoId)
                        .collect(Collectors.toSet());
                broadcastInfoIds.removeAll(collect);
                results.addAll(broadcastInfoIds);
            }
        }
        return results;
    }

    private Set<String> findReceiverInfoIds(List<InfoReceiverEntity> receivers) {
        Set<String> results = null;
        if (CollectionUtils.isNotEmpty(receivers)) {
            results = receivers.parallelStream()
                    .filter(Objects::nonNull)
                    .map(InfoReceiverEntity::getInfoId)
                    .collect(Collectors.toSet());
        }
        return Optional.ofNullable(results).orElse(Sets.newHashSet());
    }

    private List<InformationEntity> selectBroadcastInfos() {
        InformationDataParam build = InformationDataParam.builder()
                .broadcastFlag(BroadCastEnum.Y)
//                .deleteFlag(DeleteFlagEnum.N)
                .build();
        QueryWrapper<InformationEntity> wrapper = queryWrapper(build);
        return list(wrapper);
    }

    private Set<String> findBroadcastInfoIds(List<InformationEntity> infos) {
        Set<String> results = null;
        if (CollectionUtils.isNotEmpty(infos)) {
            results = infos.parallelStream()
                    .filter(Objects::nonNull)
                    .filter(info -> BroadCastEnum.Y.getCode().equals(info.getBroadcastFlag()))
                    .map(InformationEntity::getId)
                    .collect(Collectors.toSet());
        }
        return Optional.ofNullable(results).orElse(Sets.newHashSet());
    }

    private Set<String> findInfoIds(List<InformationEntity> infos) {
        Set<String> results = null;
        if (CollectionUtils.isNotEmpty(infos)) {
            results = infos.parallelStream()
                    .filter(Objects::nonNull)
                    .map(InformationEntity::getId)
                    .collect(Collectors.toSet());
        }
        return Optional.ofNullable(results).orElse(Sets.newHashSet());
    }

    @Override
    public List<InformationEntity> selectInformationFromDataBase(InformationDataParam param) {
        QueryWrapper<InformationEntity> wrapper = queryWrapper(param);
        return list(wrapper);
    }

    private QueryWrapper<InformationEntity> queryWrapper(InformationDataParam param) {
        QueryWrapper<InformationEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getId())) {
            wrapper.eq(InformationEntity.ID, param.getId());
        }
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(InformationEntity.ID, param.getIds());
        }
        if (StringUtils.isNotBlank(param.getInfoTitle())) {
            wrapper.like(InformationEntity.INFO_TITLE, param.getInfoTitle());
        }
        if (Objects.nonNull(param.getInfoType()) && !InfoTypeEnum.ALL.equals(param.getInfoType())) {
            wrapper.eq(InformationEntity.INFO_TYPE, param.getInfoType().getCode());
        }
        if (StringUtils.isNotBlank(param.getSenderId())) {
            wrapper.eq(InformationEntity.SENDER_ID, param.getSenderId());
        }
        if (Objects.nonNull(param.getBroadcastFlag())) {
            wrapper.eq(InformationEntity.BROADCAST_FLAG, param.getBroadcastFlag().getCode());
        }
        if (Objects.nonNull(param.getDeleteFlag())) {
            wrapper.eq(InformationEntity.DELETE_FLAG, param.getDeleteFlag().getCode());
        }
        if (Objects.nonNull(param.getStartSendTime())) {
            wrapper.ge(InformationEntity.SEND_TIME, param.getStartSendTime());
        }
        if (Objects.nonNull(param.getEndSendTime())) {
            wrapper.le(InformationEntity.SEND_TIME, param.getEndSendTime());
        }
        wrapper.orderByDesc(InformationEntity.CREATE_TIME);
        return wrapper;
    }

    private List<UserEntity> getUsers(UserEntityConditionParam param) {
        QueryWrapper<UserEntity> wrapper = userQueryWrapper(param);
        if (CollectionUtils.isNotEmpty(param.getIds())) {
            wrapper.in(UserEntity.ID, param.getIds());
        }
        return userService.list(wrapper);
    }

    private QueryWrapper<UserEntity> userQueryWrapper(UserEntityConditionParam param) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(param.getName())) {
            wrapper.like(UserEntity.USER_NAME, param.getName());
        }
        return wrapper;
    }

    private List<InformationEntity> selectInfos(InformationDataParam param) {
        List<InformationEntity> results = null;
        //从数据库查询
        results = selectInformationFromDataBase(param);
        return results;
    }

    private List<InfoReceiverEntity> selectReceivers(InfoReceiverDataParam param) {
        List<InfoReceiverEntity> results = null;
        //从数据库查询
        results = infoReceiverService.getInfoReceiverFromDatabase(param);
        return results;
    }
}
