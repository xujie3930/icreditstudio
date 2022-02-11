package com.micro.cloud.modules.process.constant;

/**
 * 〈流程常量〉
 *
 * @author roy
 * @create 2021/12/7
 * @since 1.0.0
 */
public interface ProcessConstant {

  String CREATOR = "creatorUserName";

  /** 流程名称 */
  String PROCESS_DEFINITION_KEY = "processDefinitionKey";

  /** 网关节点id前缀 */
  String GATEWAY_LOCAL_VAR_KEY = "gateway";

  /** 当前节点id属性label */
  String CURNODE_ATTRI_ID_LABEL = "id";

  /** BPMN流程根节点XPATH */
  String BPMN_GOALELEMENT_XPATH = "//bpmn:definitions/bpmn:process";

  /** BPMN-START节点标签 */
  String BPMN_START_TAGNAME = "bpmn:startEvent";

  String BPMN_END_TAGNAME = "bpmn:endEvent";

  /** BPMN-出口标签 */
  String BPMN_OUT_GOING_TAGNAME = "bpmn:outgoing";

  /** BPMN-出口标签 */
  String BPMN_INCOMING_TAGNAME = "bpmn:incoming";

  /** BPMN-USERTASK节点的TAGNAME */
  String BPMN_USERTASK_TAGNAME = "bpmn:userTask";

  /** BPMN-SEQUENCEFLOW节点的TAGNAME */
  String BPMN_SEQUENCEFLOW_TAGNAME = "bpmn:sequenceFlow";

  /** BPMN-CAMUNDA_ASSIGNEE节点代理人的TAGNAME */
  String BPMN_CAMUNDA_ASSIGNEE_TAGNAME = "camunda:assignee";

  /** BPMN-SEQUENCEFLOW_SOURCEREF连线对象属性TAGNAME */
  String BPMN_SEQUENCEFLOW_SOURCEREF = "sourceRef";

  /** BPMN-MULTIINSTANCELOOPCHARACTERISTICS多实例节点的TAGNAME */
  String BPMN_MULTIINSTANCELOOPCHARACTERISTICS_TAGNAME = "bpmn:multiInstanceLoopCharacteristics";

  /** BPMN-CONDITIONEXPRESSION连线中的变量表达式节点标签TAGNAME */
  String BPMN_CONDITIONEXPRESSION_TAGNAME = "bpmn:conditionExpression";

  /** BPMN-CAMUNDA_COLLECTION多实例节点代理人集合的TAGNAME */
  String BPMN_CAMUNDA_COLLECTION_TAGNAME = "camunda:collection";

  /** ${xxxxxx}获取表达式中间内容正则表达式 */
  String VAREXTRACTMULTI_REGEX = "\\$\\{(.*)\\}";

  /** ${xxx&lt;/gt;/=/==}获取表达式中间内容正则表达式 */
  String OUTGOING_REGEX = "\\$\\{(.*[^=])[^&lgt;][&=]";

  /** ${checkApprove.checkHuiqian(XXX)==0}获取表达式中间内容正则表达式 */
  String GATEWAY_OUTGOING_REGEX = "\\$\\{.*\\((.*)\\).*\\}";

  /** ${xxx&lt;/gt;/=/==}获取表达式中间内容正则表达式 */
  String NEXT_OUTGOING_REGEX = "\\$\\{(.*)[\\&\\=\\;](.*[^lgt;=])\\}";
  /**
   * 流程回调接口返回结果标识
   */
  String CALLBACK_SUCCESS = "success";

}
