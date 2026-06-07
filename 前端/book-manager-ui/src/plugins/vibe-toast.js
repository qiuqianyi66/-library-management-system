// 温和提醒系统 — 替代生硬的 $message 和 $alert
// 用法: this.$vibe.info('你的书快到期了~', '📚')
//       this.$vibe.success('还书成功！', '🎉')

const containerId = 'vibe-toast-container'
const icons = {
  info: '💡',
  success: '✨',
  warning: '🌟',
  gentle: '📖'
}

function createContainer() {
  let container = document.getElementById(containerId)
  if (!container) {
    container = document.createElement('div')
    container.id = containerId
    container.style.cssText = `
      position: fixed; top: 20px; right: 20px; z-index: 99999;
      display: flex; flex-direction: column; gap: 8px;
      pointer-events: none;
    `
    document.body.appendChild(container)
  }
  return container
}

function showToast(type, message, icon, duration = 3500) {
  const container = createContainer()
  const toast = document.createElement('div')
  toast.style.cssText = `
    display: flex; align-items: center; gap: 10px;
    padding: 12px 18px;
    background: var(--vibe-bg-elevated, #fff);
    border: 1px solid var(--vibe-border-light, #ebeef5);
    border-radius: 12px;
    box-shadow: 0 8px 24px rgba(0,0,0,0.1);
    font-size: 14px;
    color: var(--vibe-text, #303133);
    pointer-events: auto;
    max-width: 360px;
    backdrop-filter: blur(12px);
    animation: vibeToastIn 0.35s ease;
    transition: all 0.3s ease;
  `
  toast.innerHTML = `
    <span style="font-size: 20px;">${icon}</span>
    <span style="flex:1; line-height:1.4;">${message}</span>
  `

  container.appendChild(toast)

  setTimeout(() => {
    toast.style.opacity = '0'
    toast.style.transform = 'translateX(30px)'
    setTimeout(() => toast.remove(), 300)
  }, duration)
}

// 注入全局
export default {
  install(Vue) {
    // 添加动画样式
    const style = document.createElement('style')
    style.textContent = `
      @keyframes vibeToastIn {
        from { opacity: 0; transform: translateX(20px) scale(0.95); }
        to { opacity: 1; transform: translateX(0) scale(1); }
      }
    `
    document.head.appendChild(style)

    Vue.prototype.$vibe = {
      info(msg, icon) { showToast('info', msg, icon || icons.info) },
      success(msg, icon) { showToast('success', msg, icon || icons.success) },
      warning(msg, icon) { showToast('warning', msg, icon || icons.warning) },
      gentle(msg, icon) { showToast('gentle', msg, icon || icons.gentle, 5000) }
    }
  }
}
