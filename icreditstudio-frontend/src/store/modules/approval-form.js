import {
  SET_FORM_CONFIG,
  SET_FORM_INFO,
  SET_MODEL_DATA,
  SET_APPROVAL_INFO,
  SET_XML_CONFIG,
  SET_BACK_XML_INFO
} from '@/store/mutation-types'

const states = () => ({
  formConfig: {},
  formInfo: {},
  modelData: [],
  approvalInfo: {},
  backXmlInfo: {},
  xmlConfig: `<?xml version="1.0" encoding="UTF-8"?>
  <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                 xmlns:flowable="http://flowable.org/bpmn"
                 xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI"
                 xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC"
                 xmlns:omgdi="http://www.omg.org/spec/DD/20100524/DI"
                 typeLanguage="http://www.w3.org/2001/XMLSchema"
                 expressionLanguage="http://www.w3.org/1999/XPath"
                 targetNamespace="http://www.flowable.org/processdef">
  <process id="Process_${new Date().getTime()}" isExecutable="true" name="流程定义名称">
  </process>
  <bpmndi:BPMNDiagram id="BpmnDiagram_1">
      <bpmndi:BPMNPlane id="BpmnPlane_1" bpmnElement="Process_${new Date().getTime()}">
      </bpmndi:BPMNPlane>
  </bpmndi:BPMNDiagram>
  </definitions>`
})

const getters = {
  formConfig: state => state.formConfig,
  formInfo: state => state.formInfo,
  modelData: state => state.modelData,
  approvalInfo: state => state.approvalInfo,
  xmlConfig: state => state.xmlConfig,
  backXmlInfo: state => state.backXmlInfo
}

const mutations = {
  [SET_FORM_CONFIG](state, params) {
    state.formConfig = params
  },
  [SET_FORM_INFO](state, params) {
    state.formInfo = params
  },
  [SET_MODEL_DATA](state, params) {
    state.modelData = params
  },
  [SET_APPROVAL_INFO](state, params) {
    state.approvalInfo = params
  },
  [SET_XML_CONFIG](state, params) {
    state.xmlConfig = params
  },
  [SET_BACK_XML_INFO](state, params) {
    state.backXmlInfo = params
  }
}

const actions = {
  setFormConfig(context, data) {
    return new Promise(resolve => {
      resolve(data)
      context.commit(SET_FORM_CONFIG, data)
    })
  },
  setFormInfo(context, data) {
    return new Promise(resolve => {
      resolve(data)
      context.commit(SET_FORM_INFO, data)
    })
  },
  setModelData(context, data) {
    return new Promise(resolve => {
      resolve(data)
      context.commit(SET_MODEL_DATA, data)
    })
  },
  setApprovalInfo(context, data) {
    return new Promise(resolve => {
      resolve(data)
      context.commit(SET_APPROVAL_INFO, data)
    })
  },
  setXmlConfig(context, data) {
    return new Promise(resolve => {
      resolve(data)
      context.commit(SET_XML_CONFIG, data)
    })
  },
  setBackXmlInfo(context, data) {
    return new Promise(resolve => {
      resolve(data)
      context.commit(SET_BACK_XML_INFO, data)
    })
  }
}

export default { state: states, getters, mutations, actions }
