/*
 * @Description: iCredit项目
 * @Date: 2021-08-30
 */
const states = () => ({
  spaceId: null // 工作空间ID
})

const getters = () => ({})

const mutations = {
  SET_SPACEID(state, spaceId) {
    state.spaceId = spaceId
  }
}

const actions = () => ({})

export default {
  state: states,
  getters,
  mutations,
  actions
}
