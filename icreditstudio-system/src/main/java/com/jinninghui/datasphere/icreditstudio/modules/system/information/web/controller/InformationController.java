package com.jinninghui.datasphere.icreditstudio.modules.system.information.web.controller;

import com.hashtech.businessframework.log.Logable;
import com.hashtech.businessframework.result.BaseController;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.jinninghui.datasphere.icreditstudio.modules.system.information.entity.InformationEntity;
import com.jinninghui.datasphere.icreditstudio.modules.system.information.service.InformationService;
import com.jinninghui.datasphere.icreditstudio.modules.system.information.service.param.*;
import com.jinninghui.datasphere.icreditstudio.modules.system.information.service.result.*;
import com.jinninghui.datasphere.icreditstudio.modules.system.information.web.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author 1
 */
@RestController
@RequestMapping("information/information")
public class InformationController extends BaseController<InformationEntity, InformationService> {

    @Autowired
    private InformationService informationService;


    @PostMapping("/inBoxPage")
    @Logable
    public BusinessResult<BusinessPageResult<InformationInBoxResult>> inBoxPage(@RequestBody InformationInBoxPageRequest request,
                                                                                @RequestHeader("x-userid") String userId) {
        InformationInBoxPageParam param = new InformationInBoxPageParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setUserId(userId);
        return informationService.inBoxPage(param);
    }

    /**
     * 发件箱列表
     *
     * @return
     */
    @PostMapping("/outBoxPage")
    @Logable
    public BusinessResult<BusinessPageResult<InformationResult>> outBoxPage(@RequestBody InformationOutBoxPageRequest request,
                                                                            @RequestHeader("x-userid") String userId) {
        InformationOutBoxPageParam param = new InformationOutBoxPageParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setUserId(userId);
        return informationService.outBoxPage(param);
    }

    /**
     * 管理批量删除
     */
    @PostMapping("/managerDelInfo")
    @Logable
    public BusinessResult<Boolean> managerDelInfo(@RequestBody InformationManagerDelInfoRequest request) {
        InformationManagerDelInfoParam param = new InformationManagerDelInfoParam();
        BeanCopyUtils.copyProperties(request, param);
        return informationService.managerDelInfo(param);
    }

    /**
     * 分页查询列表
     */
    @PostMapping("/infoManagerPage")
    @Logable
    public BusinessResult<BusinessPageResult<InformationResult>> infoManagerPage(@RequestBody InformationManagerPageRequest request) {
        InformationManagerPageParam param = new InformationManagerPageParam();
        BeanCopyUtils.copyProperties(request, param);
        return informationService.infoManagerPage(param);
    }

    @PostMapping("/userAllReadInfo")
    @Logable
    public BusinessResult<Boolean> userAllReadInfo(@RequestBody InformationAllReadRequest request,
                                                   @RequestHeader("x-userid") String userId) {
        InformationAllReadParam param = new InformationAllReadParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setUserId(userId);
        return informationService.userAllReadInfo(param);
    }

    /**
     * 信息
     */
    @GetMapping("/user/info")
    @Logable
    public BusinessResult<InfoNoticeResult> userinfo(@RequestParam("id") String id,
                                                     @RequestHeader("x-userid") String userId) {
        InformationUserInfoParam param = new InformationUserInfoParam();
        param.setId(id);
        param.setUserId(userId);
        return informationService.userInfo(param);
    }

    /**
     * 发送
     */
    @PostMapping("/send")
    @Logable
    public BusinessResult<Boolean> save(@RequestBody InformationSendRequest request,
                                        @RequestHeader("x-userid") String userId) {
        InformationSendParam param = new InformationSendParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setSenderId(userId);
        return informationService.sendInfo(param);
    }

    /**
     * 消息数量
     *
     * @return
     */
    @PostMapping("/infoCount")
    @Logable
    public BusinessResult<InfoNoticeCountResult> infoCount(@RequestBody InformationCountRequest request,
                                                           @RequestHeader("x-userid") String userId) {
        InformationCountParam param = new InformationCountParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setUserId(userId);
        return informationService.infoCount(param);
    }

    @GetMapping("/pollingUnreadInfos")
    @Logable
    public BusinessResult<InfoCountResult> pollingUnreadInfos(@RequestHeader("x-userid") String userId) {
        InformationUnreadParam param = new InformationUnreadParam();
        param.setUserId(userId);
        return informationService.pollingUnreadInfos(param);
    }

    @PostMapping("/infoNoticePage")
    @Logable
    public BusinessResult<BusinessPageResult<InfoNoticeResult>> infoNoticePage(@RequestBody InformationNoticePageRequest request,
                                                                               @RequestHeader("x-userid") String userId) {
        InformationNoticePageParam param = new InformationNoticePageParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setUserId(userId);
        return informationService.infoNoticePage(param);
    }

    @PostMapping("/deleteAllUserInfos")
    @Logable
    public BusinessResult<Boolean> deleteAllUserInfos(@RequestBody InformationDelAllUserInfoRequest request,
                                                      @RequestHeader("x-userid") String userId) {
        InformationDelAllUserInfoParam param = new InformationDelAllUserInfoParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setUserId(userId);
        return informationService.deleteAllUserInfos(param);
    }

    /**
     * 删除
     */
    @PostMapping("/deleteUserInfo")
    @Logable
    public BusinessResult<Boolean> deleteUserInfo(@RequestBody InformationDelUserInfoRequest request,
                                                  @RequestHeader("x-userid") String userId) {
        InformationDelUserInfoParam param = new InformationDelUserInfoParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setUserId(userId);
        return informationService.deleteUserInfo(param);
    }

    /**
     * @return
     */
    @PostMapping("/deleteOutBoxInfo")
    public BusinessResult<Boolean> deleteOutBoxInfo(@RequestBody InformationDelUserInfoRequest request,
                                                    @RequestHeader("x-userid") String userId) {
        InformationDelUserInfoParam param = new InformationDelUserInfoParam();
        BeanCopyUtils.copyProperties(request, param);
        param.setUserId(userId);
        return informationService.deleteOutBoxInfo(param);
    }

}
