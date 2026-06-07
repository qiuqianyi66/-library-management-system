<template>
  <div class="ambient-player" v-if="enabled">
    <div class="ambient-bar">
      <span class="ambient-icon">{{ currentIcon }}</span>
      <span class="ambient-label">{{ currentLabel }}</span>
      <span class="ambient-stop" @click="stop">✕</span>
    </div>
    <!-- 可视化音频动画 -->
    <div class="sound-wave">
      <span v-for="i in 5" :key="i" class="wave-bar" :style="{ animationDelay: i * 0.15 + 's' }"></span>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'AmbientSound',
  computed: {
    ...mapState('ambiance', {
      enabled: state => state.ambientEnabled,
      type: state => state.ambientType
    }),
    currentIcon() {
      const icons = { rain: '🌧', pages: '📖', cafe: '☕', night: '🌙' }
      return icons[this.type] || '🎵'
    },
    currentLabel() {
      const labels = { rain: '雨声白噪音', pages: '翻书声', cafe: '咖啡馆', night: '深夜电台' }
      return labels[this.type] || '氛围音'
    }
  },
  methods: {
    stop() {
      this.$store.dispatch('ambiance/setAmbient', { enabled: false })
    }
  }
}
</script>

<style scoped>
.ambient-player {
  position: fixed;
  left: 50%;
  bottom: 20px;
  transform: translateX(-50%);
  z-index: 1000;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 6px;
}
.ambient-bar {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  background: var(--vibe-bg-elevated, #fff);
  border-radius: 20px;
  box-shadow: var(--vibe-shadow, 0 4px 16px rgba(0,0,0,0.12));
  border: 1px solid var(--vibe-border-light, #ebeef5);
  font-size: 13px;
  color: var(--vibe-text, #303133);
  backdrop-filter: blur(10px);
}
.ambient-icon { font-size: 16px; }
.ambient-stop {
  cursor: pointer;
  opacity: 0.5;
  font-size: 12px;
  padding: 2px 4px;
  border-radius: 50%;
}
.ambient-stop:hover { opacity: 1; background: var(--vibe-bg-hover); }

/* 声波动画 */
.sound-wave {
  display: flex;
  align-items: center;
  gap: 3px;
  height: 20px;
}
.wave-bar {
  width: 3px;
  height: 8px;
  border-radius: 2px;
  background: var(--vibe-primary, #2563eb);
  opacity: 0.6;
  animation: waveAnim 0.8s ease-in-out infinite alternate;
}
@keyframes waveAnim {
  0% { height: 6px; }
  100% { height: 18px; }
}
</style>
