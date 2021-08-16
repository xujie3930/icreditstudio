const inputData = {
  name: '',
  type: 'text',
  selectValue: '',
  inputValue: '',
  selectType: '',
  options: [
    {
      value: 'lt',
      label: '小于'
    },
    {
      value: 'gt',
      label: '大于'
    },
    {
      value: 'lte',
      label: '小于等于'
    },
    {
      value: 'eq',
      label: '等于'
    },
    {
      value: 'gte',
      label: '大于等于'
    }
  ]
}
const inputTextData = {
  name: '',
  type: 'textOnly',
  selectValue: '',
  inputValue: '',
  selectType: '',
  options: [
    {
      value: 'contains',
      label: '完全等于'
    },
    {
      value: 'containsAny',
      label: '包含任意'
    }
  ]
}
const radioData = {
  name: '',
  type: 'radio',
  selectValue: '',
  inputValue: [],
  selectType: '',
  options: [
    {
      value: 'containsAny',
      label: '包含任意'
    }
  ],
  selectOptions: []
}
const checkboxData = {
  name: '',
  type: 'checkbox',
  selectValue: '',
  inputValue: [],
  selectType: '',
  options: [
    // {
    //   value: 'contains',
    //   label: '完全等于'
    // },
    {
      value: 'containsAny',
      label: '包含任意'
    }
  ],
  selectOptions: []
}
const selectData = {
  name: '',
  type: 'select',
  selectValue: '',
  inputValue: [],
  selectType: '',
  options: [
    // {
    //   value: 'contains',
    //   label: '属于'
    // },
    {
      value: 'containsAny',
      label: '包含任意'
    }
  ],
  selectOptions: []
}
const cascaderData = {
  name: '',
  type: 'cascader',
  selectValue: '',
  inputValue: [],
  inputOptions: [],
  selectType: '',
  options: [
    {
      value: 'contains',
      label: '属于'
    }
    // {
    //   value: 'containsAny',
    //   label: '包含任意'
    // }
  ],
  selectOptions: []
}

const TYPES = ['input', 'radio', 'checkbox', 'select', 'cascader']

class FormItem {
  /**
   * 获取 ConditionItem
   * @param item {{type: 'input' | 'radio' | 'checkbox' | 'select' | 'cascader',
   * name: String, model: String, options: Object}}
   */
  getConditionItem({ type, name, model, options }) {
    if (!TYPES.includes(type)) return {}
    const _item = this.initObjByType(type, name, model, options)
    if (!Object.keys(_item).length) return {}
    if (type !== 'input') return _item
    if (options.regexp === 'text') {
      _item.selectOptions = []
    } else {
      delete _item.selectOptions
    }
    return _item
  }

  /**
   * @param type {'input' | 'radio' | 'checkbox' | 'select' | 'cascader'}
   * @param name {String}
   * @param model {String}
   * @param options {Object}
   * @return {Object}
   */
  initObjByType(type, name, model, options) {
    const _key = this.initKeyByType(type, options.regexp)
    if (!_key) return {}
    return Object.assign(
      {},
      JSON.parse(JSON.stringify(this[_key])),
      {
        name,
        selectType: model,
        selectOptions: options[options.remote ? 'remoteOptions' : 'options']
      }
    )
  }

  /**
   * 根据type 返回key
   * @param type
   * @param regexp
   * @return {string}
   */
  initKeyByType(type, regexp) {
    if (type !== 'input') return `${type}Data`
    if (['amount', 'number', 'integer'].includes(regexp)) return 'inputData'
    if (regexp === 'text') return 'inputTextData'
    return ''
  }
}

Object.assign(
  FormItem.prototype,
  {
    inputData,
    inputTextData,
    radioData,
    checkboxData,
    selectData,
    cascaderData
  }
)
export default FormItem
