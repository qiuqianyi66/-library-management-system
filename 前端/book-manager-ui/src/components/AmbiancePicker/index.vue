<template>
  <div class="ambiance-panel">
    <!-- 浮动切换按钮 -->
    <div class="ambiance-toggle" @click="panelOpen = !panelOpen" :title="panelOpen ? '关闭控制台' : '打开氛围控制台'">
      <svg viewBox="0 0 24 24" width="22" height="22" fill="none" stroke="currentColor" stroke-width="2">
        <circle cx="12" cy="12" r="5" />
        <line x1="12" y1="1" x2="12" y2="3" />
        <line x1="12" y1="21" x2="12" y2="23" />
        <line x1="4.22" y1="4.22" x2="5.64" y2="5.64" />
        <line x1="18.36" y1="18.36" x2="19.78" y2="19.78" />
        <line x1="1" y1="12" x2="3" y2="12" />
        <line x1="21" y1="12" x2="23" y2="12" />
        <line x1="4.22" y1="19.78" x2="5.64" y2="18.36" />
        <line x1="18.36" y1="5.64" x2="19.78" y2="4.22" />
      </svg>
    </div>

    <!-- 氛围控制面板 -->
    <transition name="panel-slide">
      <div v-if="panelOpen" class="ambiance-drawer">
        <div class="ambiance-header">
          <span class="ambiance-title">🎨 氛围控制台</span>
          <button class="ambiance-close" @click="panelOpen = false">✕</button>
        </div>

        <div class="ambiance-section">
          <div class="section-label">📖 阅读主题</div>
          <div class="theme-grid">
            <div
              v-for="t in themes"
              :key="t.id"
              class="theme-item"
              :class="{ active: currentTheme === t.id }"
              @click="switchTheme(t.id)"
            >
              <div class="theme-preview" :style="{ background: t.color }">
                <span class="theme-icon">{{ t.icon }}</span>
              </div>
              <span class="theme-name">{{ t.name }}</span>
            </div>
          </div>
        </div>

        <div class="ambiance-section">
          <div class="section-label">🔊 氛围音</div>
          <div class="sound-list">
            <div
              v-for="s in sounds"
              :key="s.id"
              class="sound-item"
              :class="{ active: currentSound === s.id }"
              @click="toggleSound(s.id)"
            >
              <span class="sound-icon">{{ s.icon }}</span>
              <span class="sound-name">{{ s.name }}</span>
              <span class="sound-indicator" v-if="currentSound === s.id && ambientEnabled">▶</span>
            </div>
          </div>
        </div>

        <div class="ambiance-section">
          <div class="section-label">🦉 小书灵助手</div>
          <div class="assistant-toggle" @click="toggleAssistant">
            <span>显示助手</span>
            <span class="toggle-switch" :class="{ on: assistantEnabled }">
              <span class="toggle-knob"></span>
            </span>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'AmbiancePicker',
  data() {
    return {
      panelOpen: false,
      themes: [
        { id: 'modern', name: '现代简约', icon: '◻', color: 'linear-gradient(135deg, #2563eb, #7c3aed)' },
        { id: 'cozy', name: '复古老书房', icon: '📚', color: 'linear-gradient(135deg, #b8860b, #8b6914)' },
        { id: 'night', name: '深夜阅读角', icon: '🌙', color: 'linear-gradient(135deg, #0f172a, #1e293b)' },
        { id: 'study', name: '校园自习馆', icon: '🌿', color: 'linear-gradient(135deg, #059669, #10b981)' }
      ],
      sounds: [
        { id: 'rain', name: '雨声', icon: '🌧' },
        { id: 'pages', name: '翻书声', icon: '📖' },
        { id: 'cafe', name: '咖啡馆', icon: '☕' },
        { id: 'night', name: '深夜', icon: '🦉' }
      ]
    }
  },
  computed: {
    ...mapState('ambiance', {
      currentTheme: state => state.theme,
      ambientEnabled: state => state.ambientEnabled,
      currentSound: state => state.ambientType,
      assistantEnabled: state => state.assistantEnabled
    })
  },
  methods: {
    switchTheme(theme) {
      this.$store.dispatch('ambiance/setTheme', theme)
    },
    toggleSound(type) {
      if (this.ambientEnabled && this.currentSound === type) {
        this.$store.dispatch('ambiance/setAmbient', { enabled: false })
      } else {
        this.$store.dispatch('ambiance/setAmbient', { enabled: true, type })
      }
    },
    toggleAssistant() {
      this.$store.dispatch('ambiance/toggleAssistant')
    }
  }
}
</script>

<style scoped>
/* 浮动按钮 */
.ambiance-toggle {
  position: fixed;
  right: 20px;
  bottom: 100px;
  width: 44px;
  height: 44px;
  border-radius: 50%;
  background: var(--vibe-primary, #2563eb);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  box-shadow: var(--vibe-shadow-glow, 0 0 20px rgba(37, 99, 235, 0.3));
  z-index: 2000;
  transition: transform 0.3s ease;
}
.ambiance-toggle:hover {
  transform: scale(1.1);
}

/* 抽屉面板 */
.ambiance-drawer {
  position: fixed;
  right: 76px;
  bottom: 20px;
  width: 280px;
  background: var(--vibe-bg-elevated, #fff);
  border-radius: var(--vibe-radius-xl, 16px);
  box-shadow: var(--vibe-shadow-lg, 0 8px 32px rgba(0,0,0,0.15));
  border: 1px solid var(--vibe-border-light, #ebeef5);
  z-index: 2001;
  padding: 16px;
  max-height: 70vh;
  overflow-y: auto;
}
.ambiance-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}
.ambiance-title {
  font-weight: bold;
  font-size: 15px;
  color: var(--vibe-text, #303133);
}
.ambiance-close {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 16px;
  color: var(--vibe-text-muted, #909399);
  padding: 2px 6px;
  border-radius: 4px;
}
.ambiance-close:hover {
  background: var(--vibe-bg-hover, #f5f7fa);
}

/* 区块 */
.ambiance-section {
  margin-bottom: 16px;
}
.section-label {
  font-size: 13px;
  color: var(--vibe-text-secondary, #606266);
  margin-bottom: 10px;
  font-weight: 500;
}

/* 主题网格 */
.theme-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 8px;
}
.theme-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 10px;
  border-radius: var(--vibe-radius, 8px);
  cursor: pointer;
  transition: all 0.2s;
  border: 2px solid transparent;
}
.theme-item:hover {
  background: var(--vibe-bg-hover, #f5f7fa);
}
.theme-item.active {
  border-color: var(--vibe-primary, #2563eb);
  background: var(--vibe-primary-bg, rgba(37,99,235,0.08));
}
.theme-preview {
  width: 32px;
  height: 32px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}
.theme-icon {
  font-size: 16px;
}
.theme-name {
  font-size: 12px;
  color: var(--vibe-text, #303133);
}

/* 音效列表 */
.sound-list {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 6px;
}
.sound-item {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 8px 10px;
  border-radius: var(--vibe-radius, 8px);
  cursor: pointer;
  transition: all 0.2s;
  font-size: 13px;
  color: var(--vibe-text-secondary, #606266);
}
.sound-item:hover {
  background: var(--vibe-bg-hover, #f5f7fa);
}
.sound-item.active {
  background: var(--vibe-primary-bg, rgba(37,99,235,0.08));
  color: var(--vibe-primary, #2563eb);
}
.sound-icon {
  font-size: 16px;
}
.sound-indicator {
  margin-left: auto;
  animation: pulse 1s infinite;
}

/* 助手开关 */
.assistant-toggle {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
  font-size: 13px;
  color: var(--vibe-text, #303133);
  cursor: pointer;
}
.toggle-switch {
  width: 40px;
  height: 22px;
  border-radius: 11px;
  background: var(--vibe-border, #e4e7ed);
  position: relative;
  transition: background 0.3s;
}
.toggle-switch.on {
  background: var(--vibe-primary, #2563eb);
}
.toggle-knob {
  position: absolute;
  top: 2px;
  left: 2px;
  width: 18px;
  height: 18px;
  border-radius: 50%;
  background: white;
  transition: transform 0.3s;
  box-shadow: 0 1px 3px rgba(0,0,0,0.2);
}
.toggle-switch.on .toggle-knob {
  transform: translateX(18px);
}

/* 面板滑入动画 */
.panel-slide-enter-active {
  animation: slideIn 0.3s ease;
}
.panel-slide-leave-active {
  animation: slideOut 0.2s ease;
}
@keyframes slideIn {
  from { opacity: 0; transform: translateY(20px) scale(0.95); }
  to { opacity: 1; transform: translateY(0) scale(1); }
}
@keyframes slideOut {
  from { opacity: 1; transform: translateY(0) scale(1); }
  to { opacity: 0; transform: translateY(20px) scale(0.95); }
}
@keyframes pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.3; }
}
</style>
