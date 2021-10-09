//package org.apache.dolphinscheduler.api.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import org.apache.dolphinscheduler.common.enums.*;
//
//import java.text.ParseException;
//import java.util.Map;
//
///**
// * @author Peng
// */
//public interface DolphinSchedulerService {
//
//    /**
//     * create project
//     *
//     * @param userId login user
//     * @param name   project name
//     * @param desc   description
//     * @return returns an error if it exists
//     */
////    Map<String, Object> createProject(/*User loginUser*/String userId, String name, String desc);
//
//    /**
//     * create process definition
//     *
//     * @param userCode              login user
//     * @param projectCode           project name
//     * @param name                  process definition name
//     * @param processDefinitionJson process definition json
//     * @param desc                  description
//     * @param locations             locations for nodes
//     * @param connects              connects for nodes
//     * @return create result code
//     * @throws JsonProcessingException JsonProcessingException
//     */
//    Map<String, Object> createProcessDefinition(/*User loginUser*/String userCode,
//                                                                  Long projectCode,
//                                                                  String name,
//                                                                  String processDefinitionJson,
//                                                                  String desc,
//                                                                  String locations,
//                                                                  String connects) throws JsonProcessingException;
//
//    /**
//     * release process definition: online / offline
//     *
//     * @param userCode     login user
//     * @param projectName  project name
//     * @param id           process definition id
//     * @param releaseState release state
//     * @return release result code
//     */
//    Map<String, Object> releaseProcessDefinition(/*User loginUser*/String userCode,
//                                                                   String projectName,
//                                                                   int id,
//                                                                   int releaseState);
//
//    /**
//     * execute process instance
//     *
//     * @param userCode                login user
//     * @param projectCode             project name
//     * @param processDefinitionId     process Definition Id
//     * @param cronTime                cron time
//     * @param commandType             command type
//     * @param failureStrategy         failuer strategy
//     * @param startNodeList           start nodelist
//     * @param taskDependType          node dependency type
//     * @param warningType             warning type
//     * @param warningGroupId          notify group id
//     * @param receivers               receivers
//     * @param receiversCc             receivers cc
//     * @param processInstancePriority process instance priority
//     * @param workerGroup             worker group name
//     * @param runMode                 run mode
//     * @param timeout                 timeout
//     * @return execute process instance code
//     * @throws ParseException Parse Exception
//     */
//    Map<String, Object> execProcessInstance(/*User loginUser*/String userCode, Long projectCode,
//                                                              String processDefinitionId, String cronTime, CommandType commandType,
//                                                              FailureStrategy failureStrategy, String startNodeList,
//                                                              TaskDependType taskDependType, WarningType warningType, String warningGroupId,
//                                                              String receivers, String receiversCc, RunMode runMode,
//                                                              Priority processInstancePriority, String workerGroup, Integer timeout) throws ParseException;
//}
