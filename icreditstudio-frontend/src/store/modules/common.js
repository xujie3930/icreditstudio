import {
  COMMON_TOGGLE_COLLAPSE,
  SET_COPYRIGHT,
  COMMON_TOGGLE_HEADER_COLLAPSE
} from '@/store/mutation-types'

const states = () => ({
  isHeaderCollapse: false,
  isCollapse: false,
  copyright: ''
})

const getters = {
  isHeaderCollapse: state => state.isHeaderCollapse,
  isCollapse: state => state.isCollapse,
  copyright: state => state.copyright
}

const mutations = {
  [COMMON_TOGGLE_COLLAPSE](state, value) {
    state.isCollapse = value
  },

  [COMMON_TOGGLE_HEADER_COLLAPSE](state, value) {
    state.isHeaderCollapse = value
  },

  [SET_COPYRIGHT](state, value) {
    state.copyright = value
  }
}

const actions = {
  toggleCollapseActions({ commit }, collapse) {
    commit(COMMON_TOGGLE_COLLAPSE, collapse)
  },

  toggleHeaderCollapseActions({ commit }, collapse) {
    commit(COMMON_TOGGLE_HEADER_COLLAPSE, collapse)
  },

  settingCopyrightAction({ commit }, copyright) {
    commit(SET_COPYRIGHT, copyright)
  }
}

export default { state: states, getters, mutations, actions }
