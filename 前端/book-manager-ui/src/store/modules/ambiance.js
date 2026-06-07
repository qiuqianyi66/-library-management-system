const storageSetting = JSON.parse(localStorage.getItem('ambiance-setting')) || {}

const state = {
  // 主题: modern | cozy | night | study
  theme: storageSetting.theme || 'modern',
  // 是否启用白噪音
  ambientEnabled: storageSetting.ambientEnabled || false,
  // 白噪音类型: rain | pages | cafe | night
  ambientType: storageSetting.ambientType || 'rain',
  // 是否启用拟人助手
  assistantEnabled: storageSetting.assistantEnabled !== false
}

const mutations = {
  SET_THEME: (state, theme) => {
    state.theme = theme
  },
  SET_AMBIENT: (state, { enabled, type }) => {
    if (enabled !== undefined) state.ambientEnabled = enabled
    if (type !== undefined) state.ambientType = type
  },
  SET_ASSISTANT: (state, enabled) => {
    state.assistantEnabled = enabled
  }
}

const actions = {
  setTheme({ commit, dispatch }, theme) {
    commit('SET_THEME', theme)
    // 持久化
    const setting = JSON.parse(localStorage.getItem('ambiance-setting')) || {}
    setting.theme = theme
    localStorage.setItem('ambiance-setting', JSON.stringify(setting))
    // 应用到 document
    document.documentElement.setAttribute('data-theme', theme)
  },
  setAmbient({ commit }, payload) {
    commit('SET_AMBIENT', payload)
    const setting = JSON.parse(localStorage.getItem('ambiance-setting')) || {}
    Object.assign(setting, payload)
    localStorage.setItem('ambiance-setting', JSON.stringify(setting))
  },
  toggleAssistant({ commit, state }) {
    const enabled = !state.assistantEnabled
    commit('SET_ASSISTANT', enabled)
    const setting = JSON.parse(localStorage.getItem('ambiance-setting')) || {}
    setting.assistantEnabled = enabled
    localStorage.setItem('ambiance-setting', JSON.stringify(setting))
  },
  // 初始化主题
  initTheme({ state }) {
    document.documentElement.setAttribute('data-theme', state.theme)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
